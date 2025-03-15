package com.example.employeeManage.service;

import com.example.employeeManage.model.Schedule;
import com.example.employeeManage.model.Employee;
import com.example.employeeManage.repository.ScheduleRepository;
import com.example.employeeManage.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Creates a schedule with conflict and work hour validation
     */
    @Transactional
    public Schedule createSchedule(Schedule schedule) {
        Employee employee = employeeRepository.findById(schedule.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!employee.isAvailable()) {
            throw new RuntimeException("Employee is not available for scheduling.");
        }

        // Check for overlapping schedules
        List<Schedule> existingSchedules = scheduleRepository.findByEmployee_Id(employee.getId());
        for (Schedule existing : existingSchedules) {
            if (schedulesOverlap(existing, schedule)) {
                throw new RuntimeException("Schedule conflict! Employee is already assigned to another shift.");
            }
        }

        // Ensure employee does not exceed daily work limit (e.g., 8 hours)
        long totalWorkHours = existingSchedules.stream()
                .mapToLong(s -> s.getEndTime().getHour() - s.getStartTime().getHour())
                .sum();

        long newScheduleHours = schedule.getEndTime().getHour() - schedule.getStartTime().getHour();
        if (totalWorkHours + newScheduleHours > 8) {
            throw new RuntimeException("Work hour limit exceeded! Cannot assign more than 8 hours per day.");
        }

        return scheduleRepository.save(schedule);
    }

    /**
     * Auto-assigns an available employee to a schedule
     */
    @Transactional
    public Schedule autoAssignSchedule(Schedule schedule) {
        List<Employee> availableEmployees = employeeRepository.findAll().stream()
                .filter(Employee::isAvailable) // ✅ Uses the fixed method
                .collect(Collectors.toList());

        if (availableEmployees.isEmpty()) {
            throw new RuntimeException("No available employees for this shift.");
        }

        schedule.setEmployee(availableEmployees.get(0)); // Assign the first available employee
        return createSchedule(schedule);
    }

    /**
     * Checks if two schedules overlap
     */
    private boolean schedulesOverlap(Schedule existing, Schedule newSchedule) {
        return (newSchedule.getStartTime().isBefore(existing.getEndTime()) &&
                newSchedule.getEndTime().isAfter(existing.getStartTime()));
    }

    /**
     * Retrieves all schedules for a specific employee (cached for better performance)
     */
    @Cacheable(value = "schedules", key = "#employeeId")
    public List<Schedule> getEmployeeSchedules(Long employeeId) {
        return scheduleRepository.findByEmployee_Id(employeeId);
    }

    /**
     * Retrieves schedules based on schedule type
     */
    public List<Schedule> getSchedulesByType(Schedule.ScheduleType type) {
        return scheduleRepository.findByType(type);
    }

    /**
     * Retrieves schedules within a specific date range
     */
    public List<Schedule> getSchedulesBetweenDates(LocalDateTime start, LocalDateTime end) {
        return scheduleRepository.findByStartTimeBetween(start, end);
    }

    /**
     * Retrieves an employee's schedules within a specific date range
     */
    public List<Schedule> getEmployeeSchedulesByDateRange(Long employeeId, LocalDateTime start, LocalDateTime end) {
        return scheduleRepository.findByEmployeeAndDateRange(employeeId, start, end);
    }

    /**
     * Retrieves a schedule by ID
     */
    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }
}
