package com.duoc.LearningPlatform.model.dto;

import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class GradeUpdateDTO {

    private String name;

    @Positive(message = "La puntuación máxima debe ser un número positivo")
    private Integer maxScore;

    private LocalDate applicationDate;

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
