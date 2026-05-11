package com.duoc.LearningPlatform.service.impl;

import com.duoc.LearningPlatform.exception.ResourceNotFoundException;
import com.duoc.LearningPlatform.model.Course;
import com.duoc.LearningPlatform.model.Grade;
import com.duoc.LearningPlatform.model.dto.GradeRequestDTO;
import com.duoc.LearningPlatform.model.dto.GradeResponseDTO;
import com.duoc.LearningPlatform.model.dto.GradeUpdateDTO;
import com.duoc.LearningPlatform.repository.CourseRepository;
import com.duoc.LearningPlatform.repository.GradeRepository;
import com.duoc.LearningPlatform.service.GradeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final CourseRepository courseRepository;

    public GradeServiceImpl(GradeRepository gradeRepository, CourseRepository courseRepository) {
        this.gradeRepository  = gradeRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<GradeResponseDTO> getAll() {
        return gradeRepository.findAll().stream()
                .map(GradeResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeResponseDTO> getByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + courseId);
        }
        return gradeRepository.findByCourseId(courseId).stream()
                .map(GradeResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public GradeResponseDTO create(GradeRequestDTO dto) {
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + dto.getCourseId()));
        Grade grade = new Grade();
        grade.setName(dto.getName());
        grade.setMaxScore(dto.getMaxScore());
        grade.setApplicationDate(dto.getApplicationDate());
        grade.setCourse(course);
        return new GradeResponseDTO(gradeRepository.save(grade));
    }

    @Override
    public GradeResponseDTO update(Long id, GradeUpdateDTO dto) {
        Grade existing = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluación no encontrada con id: " + id));
        if (dto.getName() != null && !dto.getName().isBlank()) existing.setName(dto.getName());
        if (dto.getMaxScore() != null) existing.setMaxScore(dto.getMaxScore());
        if (dto.getApplicationDate() != null) existing.setApplicationDate(dto.getApplicationDate());
        if (dto.getCourseId() != null) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + dto.getCourseId()));
            existing.setCourse(course);
        }
        return new GradeResponseDTO(gradeRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!gradeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Evaluación no encontrada con id: " + id);
        }
        gradeRepository.deleteById(id);
    }
}
