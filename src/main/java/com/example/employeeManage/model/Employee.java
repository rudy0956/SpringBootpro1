package com.example.employeeManage.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private LocalDate hireDate;
    private String department;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @ElementCollection
    private List<String> skills;

    @OneToMany(mappedBy = "employee")
    private List<WellnessScore> wellnessScores;

    @OneToMany(mappedBy = "employee")
    private List<Schedule> schedules;

    public enum EmployeeStatus {
        ACTIVE, INACTIVE, ON_LEAVE, PROBATION
    }

    // ✅ Added isAvailable() method
    public boolean isAvailable() {
        return this.status == EmployeeStatus.ACTIVE;  // Only ACTIVE employees are available
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public EmployeeStatus getStatus() { return status; }
    public void setStatus(EmployeeStatus status) { this.status = status; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public List<WellnessScore> getWellnessScores() { return wellnessScores; }
    public void setWellnessScores(List<WellnessScore> wellnessScores) { this.wellnessScores = wellnessScores; }

    public List<Schedule> getSchedules() { return schedules; }
    public void setSchedules(List<Schedule> schedules) { this.schedules = schedules; }
}
