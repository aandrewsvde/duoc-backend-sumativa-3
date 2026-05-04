package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.model.Grade;

import java.util.List;

public interface GradeService {

    List<Grade> getAll();

    List<Grade> getByCourseId(Long courseId);

    Grade create(Grade grade, Long courseId);

    Grade update(Long id, Grade grade);
}
