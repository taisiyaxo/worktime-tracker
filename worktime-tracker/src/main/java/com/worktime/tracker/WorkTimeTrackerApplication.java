package com.worktime.tracker;

import com.worktime.tracker.model.ERole;
import com.worktime.tracker.model.Role;
import com.worktime.tracker.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WorkTimeTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkTimeTrackerApplication.class, args);
	}

	@Bean
	public CommandLineRunner initRoles(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
				roleRepository.save(new Role(ERole.ROLE_USER));
			}
			if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
				roleRepository.save(new Role(ERole.ROLE_ADMIN));
			}
		};
	}
}