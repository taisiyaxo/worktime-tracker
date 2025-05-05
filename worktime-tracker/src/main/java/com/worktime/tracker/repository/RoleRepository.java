package com.worktime.tracker.repository;

import com.worktime.tracker.model.ERole;
import com.worktime.tracker.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
