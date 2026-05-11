package com.duoc.LearningPlatform.service.impl;

import com.duoc.LearningPlatform.exception.ResourceNotFoundException;
import com.duoc.LearningPlatform.model.Enrollment;
import com.duoc.LearningPlatform.model.Payment;
import com.duoc.LearningPlatform.model.dto.PaymentRequestDTO;
import com.duoc.LearningPlatform.model.dto.PaymentResponseDTO;
import com.duoc.LearningPlatform.repository.EnrollmentRepository;
import com.duoc.LearningPlatform.repository.PaymentRepository;
import com.duoc.LearningPlatform.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              EnrollmentRepository enrollmentRepository) {
        this.paymentRepository    = paymentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public List<PaymentResponseDTO> getAll() {
        return paymentRepository.findAll().stream()
                .map(PaymentResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponseDTO getById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id: " + id));
        return new PaymentResponseDTO(payment);
    }

    @Override
    public PaymentResponseDTO create(PaymentRequestDTO dto) {
        Enrollment enrollment = enrollmentRepository.findById(dto.getEnrollmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inscripción no encontrada con id: " + dto.getEnrollmentId()));
        Payment payment = new Payment();
        payment.setEnrollment(enrollment);
        payment.setAmount(dto.getAmount());
        if (dto.getStatus() != null) payment.setStatus(dto.getStatus());
        return new PaymentResponseDTO(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponseDTO update(Long id, PaymentRequestDTO dto) {
        Payment existing = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id: " + id));
        if (dto.getAmount() != null) existing.setAmount(dto.getAmount());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        if (dto.getEnrollmentId() != null) {
            Enrollment enrollment = enrollmentRepository.findById(dto.getEnrollmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Inscripción no encontrada con id: " + dto.getEnrollmentId()));
            existing.setEnrollment(enrollment);
        }
        return new PaymentResponseDTO(paymentRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pago no encontrado con id: " + id);
        }
        paymentRepository.deleteById(id);
    }
}
