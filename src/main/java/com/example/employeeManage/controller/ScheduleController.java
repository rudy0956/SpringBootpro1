package com.example.employeeManage.controller;

import com.example.employeeManage.model.Schedule;
import com.example.employeeManage.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * Creates a new schedule
     */
    @PostMapping("/create")
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        return ResponseEntity.ok(scheduleService.createSchedule(schedule));
    }

    /**
     * Auto-assigns an employee and creates a schedule
     */
    @PostMapping("/auto-assign")
    public ResponseEntity<Schedule> autoAssignSchedule(@RequestBody Schedule schedule) {
        return ResponseEntity.ok(scheduleService.autoAssignSchedule(schedule));
    }

    /**
     * Retrieves schedules for a specific employee
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Schedule>> getEmployeeSchedules(@PathVariable Long employeeId) {
        return ResponseEntity.ok(scheduleService.getEmployeeSchedules(employeeId));
    }

    /**
     * Retrieves schedules within a date range
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<Schedule>> getSchedulesBetweenDates(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(scheduleService.getSchedulesBetweenDates(start, end));
    }
}
