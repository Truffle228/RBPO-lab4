package com.example.lab2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "releases")
public class Release {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private SoftwareProduct product;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime releaseDate;

    public Release() {}
    public Release(SoftwareProduct product, String number, String status) {
        this.product = product;
        this.number = number;
        this.status = status;
        this.releaseDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public SoftwareProduct getProduct() { return product; }
    public void setProduct(SoftwareProduct product) { this.product = product; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDateTime releaseDate) { this.releaseDate = releaseDate; }
}
