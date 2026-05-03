package com.duoc.LearningPlatform.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * Vista de un curso para el catálogo público de estudiantes.
 * No expone la entidad JPA ni datos sensibles.
 */
public class CourseCatalogDTO {

    private Long id;
    private String title;
    private String description;
    private String teacherName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public CourseCatalogDTO() {}

    public CourseCatalogDTO(Long id, String title, String description,
                            String teacherName, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.teacherName = teacherName;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
