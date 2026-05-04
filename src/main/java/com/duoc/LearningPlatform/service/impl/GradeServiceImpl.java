package com.duoc.LearningPlatform.service.impl;

import com.duoc.LearningPlatform.exception.ResourceNotFoundException;
import com.duoc.LearningPlatform.model.Course;
import com.duoc.LearningPlatform.model.Grade;
import com.duoc.LearningPlatform.repository.CourseRepository;
import com.duoc.LearningPlatform.repository.GradeRepository;
import com.duoc.LearningPlatform.service.GradeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final CourseRepository courseRepository;

    public GradeServiceImpl(GradeRepository gradeRepository, CourseRepository courseRepository) {
        this.gradeRepository = gradeRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Grade> getAll() {
        return gradeRepository.findAll();
    }

    @Override
    public List<Grade> getByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + courseId);
        }
        return gradeRepository.findByCourseId(courseId);
    }

    @Override
    public Grade create(Grade grade, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + courseId));
        grade.setCourse(course);
        return gradeRepository.save(grade);
    }

    @Override
    public Grade update(Long id, Grade grade) {
        Grade existing = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluación no encontrada con id: " + id));
        existing.setName(grade.getName());
        existing.setMaxScore(grade.getMaxScore());
        existing.setApplicationDate(grade.getApplicationDate());
        if (grade.getCourse() != null && grade.getCourse().getId() != null) {
            Course course = courseRepository.findById(grade.getCourse().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + grade.getCourse().getId()));
            existing.setCourse(course);
        }
        return gradeRepository.save(existing);
    }
}
