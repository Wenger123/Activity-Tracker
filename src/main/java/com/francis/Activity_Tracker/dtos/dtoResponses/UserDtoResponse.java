package com.francis.Activity_Tracker.dtos.dtoResponses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoResponse {
    private Long id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email;
    private String password;
}
