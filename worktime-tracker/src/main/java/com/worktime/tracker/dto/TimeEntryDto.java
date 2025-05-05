package com.worktime.tracker.dto;


import com.worktime.tracker.model.TimeEntry;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeEntryDto {
    private Long id;
    private Long taskId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String comment;

    public TimeEntryDto(TimeEntry entry) {
        this.id = entry.getId();
        this.taskId = entry.getTask().getId();
        this.startTime = entry.getStartTime();
        this.endTime = entry.getEndTime();
        this.comment = entry.getComment();
    }
}
