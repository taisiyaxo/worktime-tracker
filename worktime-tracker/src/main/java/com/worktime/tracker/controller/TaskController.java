package com.worktime.tracker.controller;

import com.worktime.tracker.dto.TaskCreateUpdateDto;
import com.worktime.tracker.dto.TaskDto;
import com.worktime.tracker.model.Task;
import com.worktime.tracker.service.TaskService;
import com.worktime.tracker.service.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<TaskDto> getMyTasks(Authentication auth) {
        Long userId = ((UserDetailsImpl) auth.getPrincipal()).getId();
        return taskService.getAllTasks(userId);
    }

    @GetMapping("/{id}")
    public TaskDto getTask(@PathVariable Long id, Authentication auth) {
        Long userId = ((UserDetailsImpl) auth.getPrincipal()).getId();
        return taskService.getTask(id, userId);
    }

    @PostMapping
    public TaskDto createTask(
            @Valid @RequestBody TaskCreateUpdateDto dto,
            Authentication auth
    ) {
        Long userId = ((UserDetailsImpl) auth.getPrincipal()).getId();
        return taskService.createTask(userId, dto);
    }

    @PutMapping("/{id}")
    public TaskDto updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskCreateUpdateDto dto,
            Authentication auth
    ) {
        Long userId = ((UserDetailsImpl) auth.getPrincipal()).getId();
        return taskService.updateTask(id, userId, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id, Authentication auth) {
        Long userId = ((UserDetailsImpl) auth.getPrincipal()).getId();
        taskService.deleteTask(id, userId);
    }
}
