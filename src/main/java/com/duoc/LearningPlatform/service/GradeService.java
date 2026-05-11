package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.model.dto.GradeRequestDTO;
import com.duoc.LearningPlatform.model.dto.GradeResponseDTO;
import com.duoc.LearningPlatform.model.dto.GradeUpdateDTO;

import java.util.List;

public interface GradeService {

    List<GradeResponseDTO> getAll();

    List<GradeResponseDTO> getByCourseId(Long courseId);

    GradeResponseDTO create(GradeRequestDTO dto);

    GradeResponseDTO update(Long id, GradeUpdateDTO dto);

    void delete(Long id);
}
