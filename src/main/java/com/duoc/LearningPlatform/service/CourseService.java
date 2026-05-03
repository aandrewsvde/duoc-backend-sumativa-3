package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.model.Course;
import com.duoc.LearningPlatform.model.Enrollment;
import com.duoc.LearningPlatform.model.User;
import com.duoc.LearningPlatform.model.dto.CourseCatalogDTO;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> findAll();

    Optional<Course> findById(Long id);

    Course create(Course course);

    Course update(Long id, Course course);

    void delete(Long id);

    Enrollment enroll(Long studentId, Long courseId);

    List<User> getStudentsByCourse(Long courseId);

    /**
     * Retorna los cursos activos ordenados: primero por título (A-Z),
     * luego por fecha de creación (más reciente primero).
     * Utiliza un HashMap como catálogo en memoria y un ArrayList para el ordenamiento.
     */
    List<CourseCatalogDTO> getActiveCoursesSorted();
}
