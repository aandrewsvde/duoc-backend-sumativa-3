package com.duoc.LearningPlatform.controller;

import com.duoc.LearningPlatform.model.dto.EnrollmentRequestDTO;
import com.duoc.LearningPlatform.model.dto.EnrollmentResponseDTO;
import com.duoc.LearningPlatform.model.dto.EnrollmentUpdateDTO;
import com.duoc.LearningPlatform.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDTO>> getAll() {
        return ResponseEntity.ok(enrollmentService.getAll());
    }

    @GetMapping("/curso/{courseId}")
    public ResponseEntity<List<EnrollmentResponseDTO>> getByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.getByCourseId(courseId));
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponseDTO> create(@Valid @RequestBody EnrollmentRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(enrollmentService.create(dto.getStudentId(), dto.getCourseId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDTO> update(@PathVariable Long id,
                                                        @Valid @RequestBody EnrollmentUpdateDTO dto) {
        return ResponseEntity.ok(enrollmentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enrollmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
