package com.example.lab2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "development_tasks")
public class DevelopmentTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private SoftwareProduct product;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @Column(nullable = false)
    private String version;

    @Column(nullable = false)
    private String status;

    public DevelopmentTask() {}
    public DevelopmentTask(SoftwareProduct product, User assignee, String version, String status) {
        this.product = product;
        this.assignee = assignee;
        this.version = version;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public SoftwareProduct getProduct() { return product; }
    public void setProduct(SoftwareProduct product) { this.product = product; }
    public User getAssignee() { return assignee; }
    public void setAssignee(User assignee) { this.assignee = assignee; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
