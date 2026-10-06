package com.campusconnectapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "post_images")
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private LostFoundPost post;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "image_order", nullable = false)
    private Byte imageOrder;


    // No-argument constructor
    public PostImage() {
    }


    // All-argument constructor
    public PostImage(Long id, LostFoundPost post,
                     String imageUrl, Byte imageOrder) {

        this.id = id;
        this.post = post;
        this.imageUrl = imageUrl;
        this.imageOrder = imageOrder;
    }


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LostFoundPost getPost() {
        return post;
    }

    public void setPost(LostFoundPost post) {
        this.post = post;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Byte getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(Byte imageOrder) {
        this.imageOrder = imageOrder;
    }
}