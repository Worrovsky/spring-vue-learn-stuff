package com.example.web;

import com.example.web.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    @GetMapping("/signup")
    public ModelAndView signup() {
        return new ModelAndView("registrationPage", "user", new User());
    }

    @PostMapping("/user/register")
    public ModelAndView registerUser() {
        return new ModelAndView("redirect:/login");
    }
}
