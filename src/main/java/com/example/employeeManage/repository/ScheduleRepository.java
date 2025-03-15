package com.example.employeeManage.repository;

import com.example.employeeManage.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByEmployee_Id(Long employeeId);
    List<Schedule> findByType(Schedule.ScheduleType type);
    List<Schedule> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT s FROM Schedule s WHERE s.employee.id = :employeeId AND s.startTime BETWEEN :start AND :end")
    List<Schedule> findByEmployeeAndDateRange(Long employeeId, LocalDateTime start, LocalDateTime end);
}
