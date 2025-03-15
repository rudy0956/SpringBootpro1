package com.example.employeeManage.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wellness_scores")
public class WellnessScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private Integer score;

    private LocalDateTime submissionDate;

    @Enumerated(EnumType.STRING)
    private WellnessCategory category;

    public enum WellnessCategory {
        PHYSICAL, MENTAL, EMOTIONAL, SOCIAL, OVERALL
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public LocalDateTime getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDateTime submissionDate) { this.submissionDate = submissionDate; }

    public WellnessCategory getCategory() { return category; }
    public void setCategory(WellnessCategory category) { this.category = category; }
}