package com.francis.Activity_Tracker.services;

import com.francis.Activity_Tracker.dtos.dtoRequests.UserDto;
import com.francis.Activity_Tracker.entities.User;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail( String email);
    User getUserByEmail( String email);
    User RegisterUser(UserDto userDto);
    Boolean loginUser(String email, String password);
//    UserDto updateUser(String email, UserDto userDto);
    UserDto updateUser(String email, UserDto userDto);
    Boolean upDateUserQuery( String newFirstName,
                              String newLastName,
                              String newPhoneNumber,
                              String newPassword,
                             String userEmail);
    void deleteUser(Long id, String email);
}
