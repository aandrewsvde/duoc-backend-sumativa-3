package com.duoc.LearningPlatform.model.dto;

import com.duoc.LearningPlatform.model.Payment;
import com.duoc.LearningPlatform.model.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponseDTO {

    private Long id;
    private Long enrollmentId;
    private String studentName;
    private String courseTitle;
    private BigDecimal amount;
    private PaymentStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime paymentDate;

    public PaymentResponseDTO() {}

    public PaymentResponseDTO(Payment payment) {
        this.id          = payment.getId();
        this.amount      = payment.getAmount();
        this.status      = payment.getStatus();
        this.paymentDate = payment.getPaymentDate();
        if (payment.getEnrollment() != null) {
            this.enrollmentId = payment.getEnrollment().getId();
            if (payment.getEnrollment().getStudent() != null)
                this.studentName = payment.getEnrollment().getStudent().getName();
            if (payment.getEnrollment().getCourse() != null)
                this.courseTitle = payment.getEnrollment().getCourse().getTitle();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
}
