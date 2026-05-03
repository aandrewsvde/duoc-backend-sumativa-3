package com.duoc.LearningPlatform.model;

import jakarta.persistence.*;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Assignment() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
