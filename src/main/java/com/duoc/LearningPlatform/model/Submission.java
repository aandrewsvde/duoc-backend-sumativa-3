package com.duoc.LearningPlatform.model;

import jakarta.persistence.*;

@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Submission() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
