package com.duoc.LearningPlatform.repository;

import com.duoc.LearningPlatform.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findByCourseId(Long courseId);
}
