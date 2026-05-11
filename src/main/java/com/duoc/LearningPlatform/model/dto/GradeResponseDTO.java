package com.duoc.LearningPlatform.model.dto;

import com.duoc.LearningPlatform.model.Grade;

import java.time.LocalDate;

public class GradeResponseDTO {

    private Long id;
    private String name;
    private int maxScore;
    private LocalDate applicationDate;
    private Long courseId;
    private String courseTitle;

    public GradeResponseDTO() {}

    public GradeResponseDTO(Grade grade) {
        this.id              = grade.getId();
        this.name            = grade.getName();
        this.maxScore        = grade.getMaxScore();
        this.applicationDate = grade.getApplicationDate();
        if (grade.getCourse() != null) {
            this.courseId    = grade.getCourse().getId();
            this.courseTitle = grade.getCourse().getTitle();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getMaxScore() { return maxScore; }
    public void setMaxScore(int maxScore) { this.maxScore = maxScore; }

    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }
}
