package com.worktime.tracker.service;

import com.worktime.tracker.dto.TimeEntryCreateUpdateDto;
import com.worktime.tracker.dto.TimeEntryDto;
import com.worktime.tracker.model.Task;
import com.worktime.tracker.model.TimeEntry;
import com.worktime.tracker.repository.TaskRepository;
import com.worktime.tracker.repository.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeEntryService {
    @Autowired
    private TimeEntryRepository teRepo;
    @Autowired
    private TaskRepository taskRepo;

    public List<TimeEntryDto> getAllForTask(Long taskId, Long userId) {
        Task task = taskRepo.findById(taskId)
                .filter(t -> t.getOwner().getId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return teRepo.findByTaskId(taskId)
                .stream()
                .map(TimeEntryDto::new)
                .collect(Collectors.toList());
    }

    public TimeEntryDto getById(Long id, Long userId) {
        return teRepo.findById(id)
                .filter(te -> te.getTask().getOwner().getId().equals(userId))
                .map(TimeEntryDto::new)
                .orElseThrow(() -> new RuntimeException("Entry not found"));
    }

    public TimeEntryDto create(Long taskId, Long userId, TimeEntryCreateUpdateDto dto) {
        Task task = taskRepo.findById(taskId)
                .filter(t -> t.getOwner().getId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Task not found"));

        TimeEntry entry = TimeEntry.builder()
                .task(task)
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .comment(dto.getComment())
                .build();

        return new TimeEntryDto(teRepo.save(entry));
    }

    public TimeEntryDto update(Long id, Long userId, TimeEntryCreateUpdateDto dto) {
        TimeEntry e = teRepo.findById(id)
                .filter(te -> te.getTask().getOwner().getId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        e.setStartTime(dto.getStartTime());
        e.setEndTime(dto.getEndTime());
        e.setComment(dto.getComment());

        return new TimeEntryDto(teRepo.save(e));
    }

    public void delete(Long id, Long userId) {
        TimeEntry e = teRepo.findById(id)
                .filter(te -> te.getTask().getOwner().getId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Entry not found"));
        teRepo.delete(e);
    }
}

