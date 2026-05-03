package com.duoc.LearningPlatform.model;

import jakarta.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Grade() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
