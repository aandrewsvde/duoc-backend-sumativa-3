package com.duoc.LearningPlatform.controller;

import com.duoc.LearningPlatform.model.dto.GradeRequestDTO;
import com.duoc.LearningPlatform.model.dto.GradeResponseDTO;
import com.duoc.LearningPlatform.model.dto.GradeUpdateDTO;
import com.duoc.LearningPlatform.service.GradeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public ResponseEntity<List<GradeResponseDTO>> getAll() {
        return ResponseEntity.ok(gradeService.getAll());
    }

    @GetMapping("/curso/{courseId}")
    public ResponseEntity<List<GradeResponseDTO>> getByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(gradeService.getByCourseId(courseId));
    }

    @PostMapping
    public ResponseEntity<GradeResponseDTO> create(@Valid @RequestBody GradeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gradeService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeResponseDTO> update(@PathVariable Long id,
                                                   @Valid @RequestBody GradeUpdateDTO dto) {
        return ResponseEntity.ok(gradeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gradeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
