package com.adote.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "animals")
public class Animal {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String name;
    private String type; // dog, cat
    private String gender; // M, F
    private boolean available = true;
    private String imagePath; // simples: caminho local

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}
