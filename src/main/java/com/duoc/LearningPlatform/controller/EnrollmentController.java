package com.duoc.LearningPlatform.controller;

import com.duoc.LearningPlatform.model.Enrollment;
import com.duoc.LearningPlatform.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inscripciones")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAll() {
        return ResponseEntity.ok(enrollmentService.getAll());
    }

    @GetMapping("/curso/{courseId}")
    public ResponseEntity<List<Enrollment>> getByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.getByCourseId(courseId));
    }

    @PostMapping
    public ResponseEntity<Enrollment> create(@RequestBody Map<String, Long> body) {
        Long studentId = body.get("studentId");
        Long courseId = body.get("courseId");
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.create(studentId, courseId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enrollmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
