package com.campusconnectapp.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", nullable = false, unique = true, length = 50)
    private String category;

    @OneToMany(mappedBy = "category")
    private List<LostFoundPost> lostFoundPosts;


    // No-argument constructor
    public Category() {
    }

    // All-argument constructor
    public Category(Long id, String category) {
        this.id = id;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
