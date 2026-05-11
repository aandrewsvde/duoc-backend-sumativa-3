package com.duoc.LearningPlatform.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class GradeRequestDTO {

    @NotBlank(message = "El nombre de la evaluación es obligatorio")
    private String name;

    @NotNull(message = "La puntuación máxima es obligatoria")
    @Positive(message = "La puntuación máxima debe ser un número positivo")
    private Integer maxScore;

    @NotNull(message = "La fecha de aplicación es obligatoria")
    private LocalDate applicationDate;

    @NotNull(message = "El id del curso es obligatorio")
    @Positive(message = "El id del curso debe ser positivo")
    private Long courseId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getMaxScore() { return maxScore; }
    public void setMaxScore(Integer maxScore) { this.maxScore = maxScore; }

    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
}
