package com.example.employeeManage.repository;

import com.example.employeeManage.model.SentimentAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SentimentAnalysisRepository extends JpaRepository<SentimentAnalysis, Long> {
    List<SentimentAnalysis> findByEmployee_Id(Long employeeId);
    List<SentimentAnalysis> findBySentiment(SentimentAnalysis.SentimentType sentiment);
    Optional<SentimentAnalysis> findById(Long id);
}
