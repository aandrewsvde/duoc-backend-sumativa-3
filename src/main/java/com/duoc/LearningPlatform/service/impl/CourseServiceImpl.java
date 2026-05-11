package com.duoc.LearningPlatform.service.impl;

import com.duoc.LearningPlatform.exception.ResourceNotFoundException;
import com.duoc.LearningPlatform.model.Course;
import com.duoc.LearningPlatform.model.Enrollment;
import com.duoc.LearningPlatform.model.User;
import com.duoc.LearningPlatform.model.dto.CourseCatalogDTO;
import com.duoc.LearningPlatform.model.dto.CourseRequestDTO;
import com.duoc.LearningPlatform.model.dto.CourseResponseDTO;
import com.duoc.LearningPlatform.model.dto.CourseUpdateDTO;
import com.duoc.LearningPlatform.model.dto.UserResponseDTO;
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
    private boolean catalogLoaded = false;

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
        if (!catalogLoaded) {
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
                        .forEach(course -> courseCatalog.put(course.getId(), toCatalogDTO(course)));
        catalogLoaded = true;
    }

    /** Convierte una entidad Course a su DTO de catálogo. */
    private CourseCatalogDTO toCatalogDTO(Course course) {
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

    /** Convierte una entidad Course a CourseResponseDTO. */
    private CourseResponseDTO toCourseResponseDTO(Course course) {
        return new CourseResponseDTO(course);
    }

    // -------------------------------------------------------------------------
    // CRUD DE CURSOS (mantiene el catálogo sincronizado)
    // -------------------------------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponseDTO> findAll() {
        return courseRepository.findAll().stream()
                .map(this::toCourseResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseResponseDTO> findById(Long id) {
        return courseRepository.findById(id).map(this::toCourseResponseDTO);
    }

    @Override
    public CourseResponseDTO create(CourseRequestDTO dto) {
        User teacher = userRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado con id: " + dto.getTeacherId()));
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setTeacher(teacher);
        course.setActive(dto.isActive());
        Course saved = courseRepository.save(course);
        if (saved.isActive()) {
            courseCatalog.put(saved.getId(), toCatalogDTO(saved));
        }
        return toCourseResponseDTO(saved);
    }

    @Override
    public CourseResponseDTO update(Long id, CourseUpdateDTO dto) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + id));
        if (dto.getTitle() != null && !dto.getTitle().isBlank()) existing.setTitle(dto.getTitle());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getActive() != null) existing.setActive(dto.getActive());
        if (dto.getTeacherId() != null) {
            User teacher = userRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado con id: " + dto.getTeacherId()));
            existing.setTeacher(teacher);
        }
        Course saved = courseRepository.save(existing);
        if (saved.isActive()) {
            courseCatalog.put(saved.getId(), toCatalogDTO(saved));
        } else {
            courseCatalog.remove(saved.getId());
        }
        return toCourseResponseDTO(saved);
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
        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new IllegalArgumentException("El estudiante ya está inscrito en este curso");
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        return enrollmentRepository.save(enrollment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getStudentsByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + courseId));
        return enrollmentRepository.findByCourse(course)
                .stream()
                .map(enrollment -> new UserResponseDTO(enrollment.getStudent()))
                .collect(Collectors.toList());
    }
}
