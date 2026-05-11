package com.duoc.LearningPlatform.model.dto;

import jakarta.validation.constraints.Positive;

public class CourseUpdateDTO {

    private String title;

    private String description;

    @Positive(message = "El id del docente debe ser positivo")
    private Long teacherId;

    private Boolean active;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
