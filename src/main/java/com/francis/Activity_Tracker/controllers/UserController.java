package com.francis.Activity_Tracker.controllers;

import com.francis.Activity_Tracker.dtos.dtoRequests.UserDto;
import com.francis.Activity_Tracker.entities.User;
import com.francis.Activity_Tracker.repositories.UserRepository;
import com.francis.Activity_Tracker.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;
    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String defaultPage(){
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String IndexPage(){
        return "/index";
    }

    @GetMapping("/register")
    public String registerUser(Model model){
        model.addAttribute("users",new User());

        return "register";
    }
    @PostMapping("/user-register")
    public String register(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Validation failed, return to the registration page with errors
            return "register";
        } else {
            userService.RegisterUser(userDto);
            return "redirect:/login_page";
        }
    }

    @GetMapping("/login_page")
    public String loginUser(Model model){
        model.addAttribute("users",new User());
        return "login_page";
    }
    @PostMapping("/user-login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession httpSession){

       if(email.isEmpty() || password.isEmpty()){
           return "login_page";
       }
       Boolean isValidUser = userService.loginUser(email,password);
       User user = userService.findUserByEmail(email).orElse(null);

       if (isValidUser){
           assert user != null;
           httpSession.setAttribute("userName", user.getFirst_name());
           httpSession.setAttribute("userId", user.getId());
           return "redirect:/user_page";
       }else {
           return "login_page";
       }
    }

    @GetMapping("/userUpdate_page")
    public String userUpdatePage(HttpSession session, Model model) {
        User user = userRepository.findById((Long) session.getAttribute("userId")).get();
        model.addAttribute("email", user.getEmail());
        return "userUpdate_page";
    }

    @PostMapping("/userUpdate")
    public String updateUserDetails(@RequestParam("newFirstName") String newFirstName,
                                    @RequestParam("newLastName") String newLastName,
                                    @RequestParam("newPhoneNumber") String newPhoneNumber,
                                    @RequestParam("newPassword") String newPassword,
                                    @RequestParam("email") String userEmail,
                                    @ModelAttribute UserDto userDto, Model model, HttpSession session) {

        Boolean isUpdated = userService.upDateUserQuery(newFirstName,newLastName,newPhoneNumber,newPassword,userEmail);
        if (!isUpdated) {
            System.out.println("Update Failed");
        }

        System.out.println("Update Successful");
        return "redirect:/userUpdate_page";
    }

}
