package com.example.employeeManage.service;

import com.example.employeeManage.model.SentimentAnalysis;
import com.example.employeeManage.repository.SentimentAnalysisRepository;
import com.example.employeeManage.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SentimentAnalysisService {
    @Autowired
    private SentimentAnalysisRepository sentimentAnalysisRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public SentimentAnalysis analyzeSentiment(SentimentAnalysis sentimentAnalysis) {
        // Validate employee exists
        employeeRepository.findById(sentimentAnalysis.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Simple sentiment analysis logic (can be replaced with ML model)
        determineSentiment(sentimentAnalysis);

        return sentimentAnalysisRepository.save(sentimentAnalysis);
    }

    private void determineSentiment(SentimentAnalysis analysis) {
        String text = analysis.getText().toLowerCase();

        // Basic sentiment scoring (simplistic example)
        int positiveCount = countOccurrences(text, new String[]{"good", "great", "excellent", "happy", "satisfied"});
        int negativeCount = countOccurrences(text, new String[]{"bad", "poor", "terrible", "unhappy", "frustrated"});

        double sentimentScore = positiveCount - negativeCount;

        if (sentimentScore <= -2) analysis.setSentiment(SentimentAnalysis.SentimentType.VERY_NEGATIVE);
        else if (sentimentScore < 0) analysis.setSentiment(SentimentAnalysis.SentimentType.NEGATIVE);
        else if (sentimentScore == 0) analysis.setSentiment(SentimentAnalysis.SentimentType.NEUTRAL);
        else if (sentimentScore > 0 && sentimentScore < 2) analysis.setSentiment(SentimentAnalysis.SentimentType.POSITIVE);
        else analysis.setSentiment(SentimentAnalysis.SentimentType.VERY_POSITIVE);

        analysis.setSentimentScore(sentimentScore);
    }

    private int countOccurrences(String text, String[] words) {
        int count = 0;
        for (String word : words) {
            count += text.split(word).length - 1;
        }
        return count;
    }

    public List<SentimentAnalysis> getEmployeeSentimentAnalyses(Long employeeId) {
        return sentimentAnalysisRepository.findByEmployee_Id(employeeId);
    }

    public List<SentimentAnalysis> getSentimentsByType(SentimentAnalysis.SentimentType sentiment) {
        return sentimentAnalysisRepository.findBySentiment(sentiment);
    }
}