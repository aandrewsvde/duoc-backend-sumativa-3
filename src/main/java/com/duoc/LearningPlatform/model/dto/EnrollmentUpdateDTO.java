package com.duoc.LearningPlatform.model.dto;

import jakarta.validation.constraints.Positive;

public class EnrollmentUpdateDTO {

    @Positive(message = "El id del estudiante debe ser positivo")
    private Long studentId;

    @Positive(message = "El id del curso debe ser positivo")
    private Long courseId;

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
}
