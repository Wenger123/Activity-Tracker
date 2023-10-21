package com.francis.Activity_Tracker.services.ServicesImpls;

import com.francis.Activity_Tracker.dtos.dtoRequests.UserDto;
import com.francis.Activity_Tracker.entities.User;
import com.francis.Activity_Tracker.exceptions.UserAlreadyExist;
import com.francis.Activity_Tracker.exceptions.UserNotFoundException;
import com.francis.Activity_Tracker.repositories.UserRepository;
import com.francis.Activity_Tracker.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final HttpSession session;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, HttpSession session) {
        this.userRepository = userRepository;
        this.session = session;
    }

    @Override
    public Optional<User> findUserByEmail( String email) {
        return userRepository.findByEmail( email);
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }


    @Override
    public User RegisterUser(UserDto userDto) {
        User existingUser = userRepository.findByEmail(userDto.getEmail()).orElse(null);
        if (existingUser == null) {
            User newUser = new User();
            newUser.setFirst_name(userDto.getFirst_name());
            newUser.setLast_name(userDto.getLast_name());
            newUser.setEmail(userDto.getEmail());
            newUser.setPhone_number(userDto.getPhone_number());
            newUser.setPassword(userDto.getPassword());
            userRepository.save(newUser);
            return newUser;
        } else {
            throw new UserAlreadyExist("User already exists");
        }
    }
    public Boolean loginUser(String email, String password){
        User existingUser = userRepository.findByEmail(email).orElse(null);
        if (existingUser == null){
            return  false;
        }else {
            session.setAttribute("userId",existingUser.getEmail());
            return true;
        }
    }
@Override
public UserDto updateUser(String email, UserDto userDto) {
    User user = userRepository.getUserByEmail(email);
    if (user != null){
        // Update the user's details with the ones provided in the userDto
        user.setFirst_name(userDto.getFirst_name());
        user.setLast_name(userDto.getLast_name());
        user.setPhone_number(userDto.getPhone_number());
        user.setPassword(userDto.getPassword());

        // Save the updated user
        userRepository.saveAndFlush(user);

        // Convert the updated user to a DTO and return it
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setFirst_name(user.getFirst_name());
        updatedUserDto.setLast_name(user.getLast_name());
        updatedUserDto.setPhone_number(user.getPhone_number());
        updatedUserDto.setPassword(user.getPassword());

        return updatedUserDto;
    } else {
        throw new UserNotFoundException("User not found with email: " + email);
    }
}

    @Override
    @Transactional
    public Boolean upDateUserQuery(String newFirstName, String newLastName, String newPhoneNumber, String newPassword, String userEmail) {
        try {
            userRepository.updateUserQuery(newFirstName, newLastName, newPhoneNumber, newPassword, userEmail);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void deleteUser(Long id, String email) {
        userRepository.deleteByIdAndEmail(id,email);

    }
}
