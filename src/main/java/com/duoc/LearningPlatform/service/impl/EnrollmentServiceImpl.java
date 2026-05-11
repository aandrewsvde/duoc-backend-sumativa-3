package com.duoc.LearningPlatform.service.impl;

import com.duoc.LearningPlatform.exception.ResourceNotFoundException;
import com.duoc.LearningPlatform.model.Course;
import com.duoc.LearningPlatform.model.Enrollment;
import com.duoc.LearningPlatform.model.User;
import com.duoc.LearningPlatform.model.dto.EnrollmentResponseDTO;
import com.duoc.LearningPlatform.model.dto.EnrollmentUpdateDTO;
import com.duoc.LearningPlatform.repository.CourseRepository;
import com.duoc.LearningPlatform.repository.EnrollmentRepository;
import com.duoc.LearningPlatform.repository.UserRepository;
import com.duoc.LearningPlatform.service.EnrollmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 CourseRepository courseRepository,
                                 UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<EnrollmentResponseDTO> getAll() {
        return enrollmentRepository.findAll().stream()
                .map(EnrollmentResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentResponseDTO> getByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + courseId));
        return enrollmentRepository.findByCourse(course).stream()
                .map(EnrollmentResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollmentResponseDTO create(Long studentId, Long courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + courseId));
        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new IllegalArgumentException("El estudiante ya está inscrito en este curso");
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        return new EnrollmentResponseDTO(enrollmentRepository.save(enrollment));
    }

    @Override
    public void delete(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inscripción no encontrada con id: " + id);
        }
        enrollmentRepository.deleteById(id);
    }

    @Override
    public EnrollmentResponseDTO update(Long id, EnrollmentUpdateDTO dto) {
        Enrollment existing = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada con id: " + id));
        if (dto.getStudentId() != null) {
            User student = userRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + dto.getStudentId()));
            existing.setStudent(student);
        }
        if (dto.getCourseId() != null) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + dto.getCourseId()));
            existing.setCourse(course);
        }
        return new EnrollmentResponseDTO(enrollmentRepository.save(existing));
    }
}
