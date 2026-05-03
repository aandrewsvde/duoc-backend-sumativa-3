package com.duoc.LearningPlatform.repository;

import com.duoc.LearningPlatform.model.Course;
import com.duoc.LearningPlatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByTeacher(User teacher);

    List<Course> findByActive(boolean active);

    @Query("SELECT c FROM Course c JOIN FETCH c.teacher WHERE c.active = true")
    List<Course> findAllActiveWithTeacher();
}
