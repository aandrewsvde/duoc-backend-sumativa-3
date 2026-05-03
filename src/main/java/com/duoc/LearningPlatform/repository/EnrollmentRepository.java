package com.duoc.LearningPlatform.repository;

import com.duoc.LearningPlatform.model.Course;
import com.duoc.LearningPlatform.model.Enrollment;
import com.duoc.LearningPlatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByCourse(Course course);

    List<Enrollment> findByStudent(User student);

    boolean existsByStudentAndCourse(User student, Course course);
}
