package com.worktime.tracker.repository;

import com.worktime.tracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByOwnerId(Long ownerId);
    Optional<Task> findByIdAndOwnerId(Long id, Long ownerId);
}
