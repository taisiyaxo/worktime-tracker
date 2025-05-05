package com.worktime.tracker.service;

import com.worktime.tracker.dto.TaskCreateUpdateDto;
import com.worktime.tracker.dto.TaskDto;
import com.worktime.tracker.model.Task;
import com.worktime.tracker.model.User;
import com.worktime.tracker.repository.TaskRepository;
import com.worktime.tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<TaskDto> getAllTasks(Long userId) {
        return taskRepository.findByOwnerId(userId)
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    public TaskDto getTask(Long id, Long userId) {
        Task task = taskRepository.findByIdAndOwnerId(id, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return new TaskDto(task);
    }

    public TaskDto createTask(Long userId, TaskCreateUpdateDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .owner(user)
                .build();

        return new TaskDto(taskRepository.save(task));
    }

    public TaskDto updateTask(Long taskId, Long userId, TaskCreateUpdateDto dto) {
        Task task = taskRepository.findByIdAndOwnerId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());

        return new TaskDto(taskRepository.save(task));
    }

    public void deleteTask(Long id, Long userId) {
        Task task = taskRepository.findByIdAndOwnerId(id, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskRepository.delete(task);
    }
}
