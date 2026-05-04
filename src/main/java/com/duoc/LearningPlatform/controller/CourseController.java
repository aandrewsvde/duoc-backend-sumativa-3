package com.duoc.LearningPlatform.controller;

import com.duoc.LearningPlatform.model.Course;
import com.duoc.LearningPlatform.model.Enrollment;
import com.duoc.LearningPlatform.model.User;
import com.duoc.LearningPlatform.model.dto.CourseCatalogDTO;
import com.duoc.LearningPlatform.service.CourseService;
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
     * GET /api/courses/active
     * Devuelve la lista de cursos activos disponibles para estudiantes,
     * ordenados por título (A-Z) y luego por fecha de creación (más reciente primero).
     */
    @GetMapping("/active")
    public ResponseEntity<List<CourseCatalogDTO>> getActiveCoursesSorted() {
        return ResponseEntity.ok(courseService.getActiveCoursesSorted());
    }

    @GetMapping
    public ResponseEntity<List<Course>> findAll() {
        return ResponseEntity.ok(courseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable Long id) {
        return courseService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.create(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id, @RequestBody Course course) {
        return ResponseEntity.ok(courseService.update(id, course));
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
    public ResponseEntity<List<User>> getStudents(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getStudentsByCourse(courseId));
    }
}
