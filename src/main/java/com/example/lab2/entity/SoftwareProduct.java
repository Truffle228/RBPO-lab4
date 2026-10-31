package com.example.lab2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "software_products")
public class SoftwareProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "responsible_id")
    private User responsible;

    @ManyToOne
    @JoinColumn(name = "current_version_id")
    @JsonIgnore
    private Release currentVersion;

    public SoftwareProduct() {}
    public SoftwareProduct(String name, User responsible) {
        this.name = name;
        this.responsible = responsible;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public User getResponsible() { return responsible; }
    public void setResponsible(User responsible) { this.responsible = responsible; }
    public Release getCurrentVersion() { return currentVersion; }
    public void setCurrentVersion(Release currentVersion) { this.currentVersion = currentVersion; }
}
