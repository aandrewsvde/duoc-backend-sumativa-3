package com.duoc.LearningPlatform.controller;

import com.duoc.LearningPlatform.model.Grade;
import com.duoc.LearningPlatform.service.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/evaluaciones")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public ResponseEntity<List<Grade>> getAll() {
        return ResponseEntity.ok(gradeService.getAll());
    }

    @GetMapping("/curso/{courseId}")
    public ResponseEntity<List<Grade>> getByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(gradeService.getByCourseId(courseId));
    }

    @PostMapping
    public ResponseEntity<Grade> create(@RequestBody Map<String, Object> body) {
        Grade grade = new Grade();
        grade.setName((String) body.get("name"));
        grade.setMaxScore((Integer) body.get("maxScore"));
        if (body.get("applicationDate") != null) {
            grade.setApplicationDate(new java.util.Date((Long) body.get("applicationDate")));
        }
        Long courseId = Long.valueOf(body.get("courseId").toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(gradeService.create(grade, courseId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grade> update(@PathVariable Long id, @RequestBody Grade grade) {
        return ResponseEntity.ok(gradeService.update(id, grade));
    }
}
