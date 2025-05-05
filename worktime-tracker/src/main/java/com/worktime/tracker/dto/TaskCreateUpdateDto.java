package com.worktime.tracker.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskCreateUpdateDto {
    @NotBlank
    private String title;
    private String description;
}
