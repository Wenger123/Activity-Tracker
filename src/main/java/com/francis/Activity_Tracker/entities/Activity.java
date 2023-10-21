package com.francis.Activity_Tracker.entities;

import com.francis.Activity_Tracker.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Table(name = "activities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty @NotBlank @NotNull
    @Size(min = 3, max = 15)
    private String name;
    @NotEmpty @NotBlank @NotNull
    @Size(min = 20, max = 1000)
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @CreationTimestamp
    private Timestamp createdDate;
    @UpdateTimestamp
    private Timestamp upDateDate;
    private Timestamp doneDate;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
                          CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn (name = "user_id")
    private User user;


    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", upDateDate=" + upDateDate +
                ", doneDate=" + doneDate +
                '}';
    }

}
