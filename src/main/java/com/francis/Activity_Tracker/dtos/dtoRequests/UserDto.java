package com.francis.Activity_Tracker.dtos.dtoRequests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email;
    private String password;
}
