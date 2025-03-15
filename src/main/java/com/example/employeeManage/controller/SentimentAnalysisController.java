package com.example.employeeManage.controller;

import com.example.employeeManage.model.SentimentAnalysis;
import com.example.employeeManage.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sentiment-analysis")
public class SentimentAnalysisController {
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @PostMapping
    public ResponseEntity<SentimentAnalysis> analyzeSentiment(@RequestBody SentimentAnalysis sentimentAnalysis) {
        return ResponseEntity.ok(sentimentAnalysisService.analyzeSentiment(sentimentAnalysis));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<SentimentAnalysis>> getEmployeeSentimentAnalyses(@PathVariable Long employeeId) {
        return ResponseEntity.ok(sentimentAnalysisService.getEmployeeSentimentAnalyses(employeeId));
    }

    @GetMapping("/type/{sentiment}")
    public ResponseEntity<List<SentimentAnalysis>> getSentimentsByType(@PathVariable SentimentAnalysis.SentimentType sentiment) {
        return ResponseEntity.ok(sentimentAnalysisService.getSentimentsByType(sentiment));
    }
}