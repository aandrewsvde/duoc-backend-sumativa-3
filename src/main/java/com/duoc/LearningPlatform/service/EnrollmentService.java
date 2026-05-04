package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.model.Enrollment;

import java.util.List;

public interface EnrollmentService {

    List<Enrollment> getAll();

    List<Enrollment> getByCourseId(Long courseId);

    Enrollment create(Long studentId, Long courseId);

    void delete(Long id);
}
