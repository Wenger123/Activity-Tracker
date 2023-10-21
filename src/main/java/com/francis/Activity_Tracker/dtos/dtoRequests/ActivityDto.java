package com.francis.Activity_Tracker.dtos.dtoRequests;

import com.francis.Activity_Tracker.entities.User;
import com.francis.Activity_Tracker.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ActivityDto {
    private String name;
    private String description;
    private Status status;
    private Timestamp createdDate;
    private Timestamp upDateDate;
    private Timestamp doneDate;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn (name = "user_id")
    private User user;
}
