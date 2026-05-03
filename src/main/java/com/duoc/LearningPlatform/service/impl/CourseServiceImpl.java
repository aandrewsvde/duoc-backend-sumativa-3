package com.duoc.LearningPlatform.service.impl;

import com.duoc.LearningPlatform.exception.ResourceNotFoundException;
import com.duoc.LearningPlatform.model.Course;
import com.duoc.LearningPlatform.model.Enrollment;
import com.duoc.LearningPlatform.model.User;
import com.duoc.LearningPlatform.model.dto.CourseCatalogDTO;
import com.duoc.LearningPlatform.repository.CourseRepository;
import com.duoc.LearningPlatform.repository.EnrollmentRepository;
import com.duoc.LearningPlatform.repository.UserRepository;
import com.duoc.LearningPlatform.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    /**
     * Catálogo en memoria: HashMap<cursoId, DTO>.
     * Permite acceso O(1) por id y se mantiene sincronizado con la base de datos.
     * Se inicializa de forma perezosa (lazy) en la primera consulta al catálogo.
     */
    private final Map<Long, CourseCatalogDTO> courseCatalog = new HashMap<>();

    public CourseServiceImpl(CourseRepository courseRepository,
                             UserRepository userRepository,
                             EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    // -------------------------------------------------------------------------
    // CATÁLOGO DE CURSOS ACTIVOS
    // -------------------------------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public List<CourseCatalogDTO> getActiveCoursesSorted() {
        // Inicialización perezosa: carga el catálogo desde la BD la primera vez
        if (courseCatalog.isEmpty()) {
            refreshCatalog();
        }

        // Paso 1: extraer valores del HashMap a un ArrayList (colección con orden)
        ArrayList<CourseCatalogDTO> courseList = new ArrayList<>(courseCatalog.values());

        // Paso 2: ordenamiento personalizado —
        //   • Criterio principal : título en orden alfabético (A → Z), case-insensitive
        //   • Criterio secundario: fecha de creación más reciente primero
        courseList.sort(
            Comparator.comparing(CourseCatalogDTO::getTitle, String.CASE_INSENSITIVE_ORDER)
                      .thenComparing(Comparator.comparing(CourseCatalogDTO::getCreatedAt).reversed())
        );

        return courseList;
    }

    /**
     * Recarga el catálogo en memoria desde la base de datos.
     * Usa JOIN FETCH para resolver el teacher en una sola consulta.
     */
    private void refreshCatalog() {
        courseCatalog.clear();
        courseRepository.findAllActiveWithTeacher()
                        .forEach(course -> courseCatalog.put(course.getId(), toDTO(course)));
    }

    /** Convierte una entidad Course a su DTO de catálogo. */
    private CourseCatalogDTO toDTO(Course course) {
        String teacherName = (course.getTeacher() != null)
                ? course.getTeacher().getName()
                : "Sin docente asignado";
        return new CourseCatalogDTO(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                teacherName,
                course.getCreatedAt()
        );
    }

    // -------------------------------------------------------------------------
    // CRUD DE CURSOS (mantiene el catálogo sincronizado)
    // -------------------------------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course create(Course course) {
        Course saved = courseRepository.save(course);
        if (saved.isActive()) {
            courseCatalog.put(saved.getId(), toDTO(saved));
        }
        return saved;
    }

    @Override
    public Course update(Long id, Course course) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + id));
        existing.setTitle(course.getTitle());
        existing.setDescription(course.getDescription());
        existing.setActive(course.isActive());
        Course saved = courseRepository.save(existing);

        // Sincronizar catálogo en memoria
        if (saved.isActive()) {
            courseCatalog.put(saved.getId(), toDTO(saved));
        } else {
            courseCatalog.remove(saved.getId());
        }
        return saved;
    }

    @Override
    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + id);
        }
        courseRepository.deleteById(id);
        courseCatalog.remove(id);
    }

    // -------------------------------------------------------------------------
    // INSCRIPCIONES
    // -------------------------------------------------------------------------

    @Override
    public Enrollment enroll(Long studentId, Long courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + courseId));
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        return enrollmentRepository.save(enrollment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getStudentsByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + courseId));
        return enrollmentRepository.findByCourse(course)
                .stream()
                .map(Enrollment::getStudent)
                .collect(Collectors.toList());
    }
}
