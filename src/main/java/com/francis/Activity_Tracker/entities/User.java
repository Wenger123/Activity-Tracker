package com.francis.Activity_Tracker.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @NotBlank @NotEmpty
    @Size(min = 3, max = 15)
    private String First_name;
    @NotNull @NotBlank @NotEmpty
    @Size(min = 3, max = 15)
    private String Last_name;
    @NotNull @NotBlank @NotEmpty
    @Size(min = 9, max = 11)
    private String phone_number;
    @Column (unique = true)
    @NotNull @NotBlank @NotEmpty @Email
    @Size(min = 15, max = 50)
    private String email;
    @NotNull @NotBlank @NotEmpty
    @Size(min = 8, max = 20)
    private String password;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Activity> tasks;

    public void addTask(Activity task){
        if(tasks == null){
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", First_name='" + First_name + '\'' +
                ", Last_name='" + Last_name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
