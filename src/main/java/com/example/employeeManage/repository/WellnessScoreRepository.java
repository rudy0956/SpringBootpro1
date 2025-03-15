package com.example.employeeManage.repository;

import com.example.employeeManage.model.WellnessScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WellnessScoreRepository extends JpaRepository<WellnessScore, Long> {
    List<WellnessScore> findByEmployee_Id(Long employeeId);
    List<WellnessScore> findByCategory(WellnessScore.WellnessCategory category);

    @Query("SELECT AVG(w.score) FROM WellnessScore w WHERE w.employee.id = :employeeId")
    Double getAverageScoreByEmployee(Long employeeId);

    List<WellnessScore> findBySubmissionDateBetween(LocalDateTime start, LocalDateTime end);
}
