package com.example.employeeManage.service;

import com.example.employeeManage.model.WellnessScore;
import com.example.employeeManage.repository.WellnessScoreRepository;
import com.example.employeeManage.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WellnessScoreService {
    @Autowired
    private WellnessScoreRepository wellnessScoreRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public WellnessScore saveWellnessScore(WellnessScore wellnessScore) {
        // Validate employee exists
        employeeRepository.findById(wellnessScore.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        wellnessScore.setSubmissionDate(LocalDateTime.now());
        return wellnessScoreRepository.save(wellnessScore);
    }

    public List<WellnessScore> getWellnessScoresByEmployee(Long employeeId) {
        return wellnessScoreRepository.findByEmployee_Id(employeeId);
    }

    public List<WellnessScore> getWellnessScoresByCategory(WellnessScore.WellnessCategory category) {
        return wellnessScoreRepository.findByCategory(category);
    }

    public Double getAverageScoreByEmployee(Long employeeId) {
        return wellnessScoreRepository.getAverageScoreByEmployee(employeeId);
    }

    public List<WellnessScore> getWellnessScoresBetweenDates(LocalDateTime start, LocalDateTime end) {
        return wellnessScoreRepository.findBySubmissionDateBetween(start, end);
    }
}