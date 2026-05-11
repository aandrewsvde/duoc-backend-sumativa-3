package com.duoc.LearningPlatform.model.dto;

import com.duoc.LearningPlatform.model.Course;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CourseResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String teacherName;
    private boolean active;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public CourseResponseDTO() {}

    public CourseResponseDTO(Course course) {
        this.id          = course.getId();
        this.title       = course.getTitle();
        this.description = course.getDescription();
        this.active      = course.isActive();
        this.createdAt   = course.getCreatedAt();
        this.teacherName = course.getTeacher() != null ? course.getTeacher().getName() : null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
