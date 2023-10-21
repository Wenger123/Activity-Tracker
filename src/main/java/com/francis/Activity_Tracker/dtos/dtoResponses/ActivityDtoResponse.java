package com.francis.Activity_Tracker.dtos.dtoResponses;

import com.francis.Activity_Tracker.entities.User;
import com.francis.Activity_Tracker.enums.Status;
import lombok.*;


import java.sql.Timestamp;
@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ActivityDtoResponse {
    private Long id;
    private String name;
    private String description;
    private Status status;
    private Timestamp createdDate;
    private Timestamp upDateDate;
    private Timestamp doneDate;
    private User user;
}
