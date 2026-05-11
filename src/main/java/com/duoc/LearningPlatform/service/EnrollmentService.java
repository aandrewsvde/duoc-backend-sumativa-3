package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.model.dto.EnrollmentResponseDTO;
import com.duoc.LearningPlatform.model.dto.EnrollmentUpdateDTO;

import java.util.List;

public interface EnrollmentService {

    List<EnrollmentResponseDTO> getAll();

    List<EnrollmentResponseDTO> getByCourseId(Long courseId);

    EnrollmentResponseDTO create(Long studentId, Long courseId);

    EnrollmentResponseDTO update(Long id, EnrollmentUpdateDTO dto);

    void delete(Long id);
}
