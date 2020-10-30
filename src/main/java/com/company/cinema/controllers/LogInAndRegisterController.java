package com.company.cinema.controllers;

import com.company.cinema.entity.User;
import com.company.cinema.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@PreAuthorize("isAnonymous()")
public class LogInAndRegisterController {

    private final UserService userService;

    @Autowired
    public LogInAndRegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("logIn", true);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model){
        if (user.getUsername() == null || "".equals(user.getUsername()) ||
                user.getPassword() == null || "".equals(user.getPassword())) {
            model.addAttribute("logIn", true);
            model.addAttribute("invalidData");
            return "login";
        }
        User receivedUser = userService.validateUser(user);
        if (receivedUser != null){
            return "redirect:/home";
        }
        model.addAttribute("invalidData");
        model.addAttribute("logIn", true);
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model){
        model.addAttribute("registration", true);
        return "registration_page";
    }

    @PostMapping("/registration")
    public String register(User user, Model model){
        if (user.getUsername() == null || user.getPassword() == null ||
                "".equals(user.getUsername()) || "".equals(user.getPassword())){
            model.addAttribute("invalidData", true);
            model.addAttribute("registration", true);
            return "registration_page";
        }

        User savedUser = userService.saveNewUser(user);
        if(savedUser != null) {
            return "redirect:/home";
        }

        model.addAttribute("userExists", true);
        model.addAttribute("registration", true);
        return "registration_page";
    }

}
