package com.duoc.LearningPlatform.controller;

import com.duoc.LearningPlatform.model.Enrollment;
import com.duoc.LearningPlatform.model.dto.CourseCatalogDTO;
import com.duoc.LearningPlatform.model.dto.CourseRequestDTO;
import com.duoc.LearningPlatform.model.dto.CourseResponseDTO;
import com.duoc.LearningPlatform.model.dto.CourseUpdateDTO;
import com.duoc.LearningPlatform.model.dto.UserResponseDTO;
import com.duoc.LearningPlatform.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cursos")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * GET /api/cursos/active
     * Devuelve la lista de cursos activos disponibles para estudiantes,
     * ordenados por título (A-Z) y luego por fecha de creación (más reciente primero).
     */
    @GetMapping("/active")
    public ResponseEntity<List<CourseCatalogDTO>> getActiveCoursesSorted() {
        return ResponseEntity.ok(courseService.getActiveCoursesSorted());
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> findAll() {
        return ResponseEntity.ok(courseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> findById(@PathVariable Long id) {
        return courseService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CourseResponseDTO> create(@Valid @RequestBody CourseRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> update(@PathVariable Long id,
                                                    @Valid @RequestBody CourseUpdateDTO dto) {
        return ResponseEntity.ok(courseService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<Enrollment> enroll(@PathVariable Long courseId,
                                             @RequestBody Map<String, Long> body) {
        Long studentId = body.get("studentId");
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.enroll(studentId, courseId));
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<UserResponseDTO>> getStudents(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getStudentsByCourse(courseId));
    }
}
