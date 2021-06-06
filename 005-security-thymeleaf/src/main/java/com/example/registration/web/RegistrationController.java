package com.example.registration.web;

import com.example.registration.model.User;
import com.example.registration.service.UserService;
import com.example.registration.validation.EmailExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public ModelAndView signup() {
        return new ModelAndView("registrationPage", "user", new User());
    }

    @PostMapping("/user/register")
    public ModelAndView registerUser(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("registrationPage", "user", user);
        }

        try {
            userService.registerUser(user);
        } catch (EmailExistException e) {
            FieldError emailError = new FieldError("user", "email", e.getMessage());
            result.addError(emailError);
            return new ModelAndView("registrationPage", "user", user);
        }

        return new ModelAndView("redirect:/login");
    }
}
