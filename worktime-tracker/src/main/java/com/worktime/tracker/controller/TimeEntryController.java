package com.worktime.tracker.controller;

import com.worktime.tracker.dto.TimeEntryCreateUpdateDto;
import com.worktime.tracker.dto.TimeEntryDto;
import com.worktime.tracker.model.TimeEntry;
import com.worktime.tracker.service.TimeEntryService;
import com.worktime.tracker.service.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/time-entries")
public class TimeEntryController {

    @Autowired
    private TimeEntryService entryService;

    @GetMapping("/task/{taskId}")
    public List<TimeEntryDto> getEntries(
            @PathVariable Long taskId, Authentication auth
    ) {
        Long userId = ((UserDetailsImpl) auth.getPrincipal()).getId();
        return entryService.getAllForTask(taskId, userId);
    }

    @GetMapping("/{id}")
    public TimeEntryDto getEntry(@PathVariable Long id, Authentication auth) {
        Long userId = ((UserDetailsImpl) auth.getPrincipal()).getId();
        return entryService.getById(id, userId);
    }

    @PostMapping("/task/{taskId}")
    public TimeEntryDto createEntry(
            @PathVariable Long taskId,
            @Valid @RequestBody TimeEntryCreateUpdateDto dto,
            Authentication auth
    ) {
        Long userId = ((UserDetailsImpl) auth.getPrincipal()).getId();
        return entryService.create(taskId, userId, dto);
    }

    @PutMapping("/{id}")
    public TimeEntryDto updateEntry(
            @PathVariable Long id,
            @Valid @RequestBody TimeEntryCreateUpdateDto dto,
            Authentication auth
    ) {
        Long userId = ((UserDetailsImpl) auth.getPrincipal()).getId();
        return entryService.update(id, userId, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteEntry(@PathVariable Long id, Authentication auth) {
        Long userId = ((UserDetailsImpl) auth.getPrincipal()).getId();
        entryService.delete(id, userId);
    }
}
