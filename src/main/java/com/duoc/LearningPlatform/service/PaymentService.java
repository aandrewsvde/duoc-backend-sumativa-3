package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.model.dto.PaymentRequestDTO;
import com.duoc.LearningPlatform.model.dto.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {

    List<PaymentResponseDTO> getAll();

    PaymentResponseDTO getById(Long id);

    PaymentResponseDTO create(PaymentRequestDTO dto);

    PaymentResponseDTO update(Long id, PaymentRequestDTO dto);

    void delete(Long id);
}
