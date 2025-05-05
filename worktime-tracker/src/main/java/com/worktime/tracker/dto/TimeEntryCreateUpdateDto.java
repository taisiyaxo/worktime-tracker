package com.worktime.tracker.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeEntryCreateUpdateDto {
    @NotNull
    private LocalDateTime startTime;

    private LocalDateTime endTime;
    private String comment;
}
