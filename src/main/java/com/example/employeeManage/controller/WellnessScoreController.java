package com.example.employeeManage.controller;

import com.example.employeeManage.model.WellnessScore;
import com.example.employeeManage.service.WellnessScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/wellness-scores")
public class WellnessScoreController {
    @Autowired
    private WellnessScoreService wellnessScoreService;

    @PostMapping
    public ResponseEntity<WellnessScore> submitWellnessScore(@RequestBody WellnessScore wellnessScore) {
        return ResponseEntity.ok(wellnessScoreService.saveWellnessScore(wellnessScore));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<WellnessScore>> getEmployeeWellnessScores(@PathVariable Long employeeId) {
        return ResponseEntity.ok(wellnessScoreService.getWellnessScoresByEmployee(employeeId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<WellnessScore>> getWellnessScoresByCategory(@PathVariable WellnessScore.WellnessCategory category) {
        return ResponseEntity.ok(wellnessScoreService.getWellnessScoresByCategory(category));
    }

    @GetMapping("/average/{employeeId}")
    public ResponseEntity<Double> getAverageScoreByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(wellnessScoreService.getAverageScoreByEmployee(employeeId));
    }
}