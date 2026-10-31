package com.example.lab2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "release_checks")
public class ReleaseCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "release_id", nullable = false)
    @JsonIgnore
    private Release release;

    @Column(nullable = false)
    private String checkName;

    @Column(nullable = false)
    private boolean mandatory;

    private String result;

    @Column(nullable = false)
    private LocalDateTime checkedAt;

    public ReleaseCheck() {}
    public ReleaseCheck(Release release, String checkName, boolean mandatory, String result) {
        this.release = release;
        this.checkName = checkName;
        this.mandatory = mandatory;
        this.result = result;
        this.checkedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Release getRelease() { return release; }
    public void setRelease(Release release) { this.release = release; }
    public String getCheckName() { return checkName; }
    public void setCheckName(String checkName) { this.checkName = checkName; }
    public boolean isMandatory() { return mandatory; }
    public void setMandatory(boolean mandatory) { this.mandatory = mandatory; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public LocalDateTime getCheckedAt() { return checkedAt; }
    public void setCheckedAt(LocalDateTime checkedAt) { this.checkedAt = checkedAt; }
}
