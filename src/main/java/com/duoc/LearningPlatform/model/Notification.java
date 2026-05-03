package com.duoc.LearningPlatform.model;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Notification() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
