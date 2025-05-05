package com.worktime.tracker.repository;

import com.worktime.tracker.model.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {
    List<TimeEntry> findByTaskId(Long taskId);
}