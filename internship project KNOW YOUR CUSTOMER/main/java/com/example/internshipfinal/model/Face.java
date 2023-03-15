package com.example.internshipfinal.model;

import javax.persistence.*;

@Table(name = "faces")
@Entity
public class Face {

    @Id
    @GeneratedValue
    private long id;


    @Column(name = "image_path")
    private String imagePath;


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
