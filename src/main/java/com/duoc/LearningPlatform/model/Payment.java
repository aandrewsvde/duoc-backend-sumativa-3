package com.duoc.LearningPlatform.model;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Payment() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
