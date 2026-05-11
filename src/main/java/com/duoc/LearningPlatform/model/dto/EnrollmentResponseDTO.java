package com.duoc.LearningPlatform.model.dto;

import com.duoc.LearningPlatform.model.Enrollment;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class EnrollmentResponseDTO {

    private Long id;
    private Long studentId;
    private String studentName;
    private Long courseId;
    private String courseTitle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime enrollmentDate;

    public EnrollmentResponseDTO() {}

    public EnrollmentResponseDTO(Enrollment enrollment) {
        this.id             = enrollment.getId();
        this.enrollmentDate = enrollment.getEnrollmentDate();
        if (enrollment.getStudent() != null) {
            this.studentId   = enrollment.getStudent().getId();
            this.studentName = enrollment.getStudent().getName();
        }
        if (enrollment.getCourse() != null) {
            this.courseId    = enrollment.getCourse().getId();
            this.courseTitle = enrollment.getCourse().getTitle();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDateTime enrollmentDate) { this.enrollmentDate = enrollmentDate; }
}
