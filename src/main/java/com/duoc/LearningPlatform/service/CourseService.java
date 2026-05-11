package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.model.Enrollment;
import com.duoc.LearningPlatform.model.dto.CourseCatalogDTO;
import com.duoc.LearningPlatform.model.dto.CourseRequestDTO;
import com.duoc.LearningPlatform.model.dto.CourseResponseDTO;
import com.duoc.LearningPlatform.model.dto.CourseUpdateDTO;
import com.duoc.LearningPlatform.model.dto.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<CourseResponseDTO> findAll();

    Optional<CourseResponseDTO> findById(Long id);

    CourseResponseDTO create(CourseRequestDTO dto);

    CourseResponseDTO update(Long id, CourseUpdateDTO dto);

    void delete(Long id);

    Enrollment enroll(Long studentId, Long courseId);

    List<UserResponseDTO> getStudentsByCourse(Long courseId);

    /**
     * Retorna los cursos activos ordenados: primero por título (A-Z),
     * luego por fecha de creación (más reciente primero).
     * Utiliza un HashMap como catálogo en memoria y un ArrayList para el ordenamiento.
     */
    List<CourseCatalogDTO> getActiveCoursesSorted();
}
