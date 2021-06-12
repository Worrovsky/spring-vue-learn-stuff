package com.example.registration.web;

import com.example.registration.model.User;
import com.example.registration.service.UserService;
import com.example.registration.validation.EmailExistException;
import com.example.verification.VerificationService;
import com.example.verification.VerificationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger log = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationService verificationService;

    @GetMapping("/signup")
    public ModelAndView signup() {
        return new ModelAndView("registrationPage", "user", new User());
    }

    @PostMapping("/user/register")
    public ModelAndView registerUser(@Valid User user, BindingResult result) {


        if (result.hasErrors()) {
            log.info("have error: " + user);
            return new ModelAndView("registrationPage", "user", user);
        }

        try {
            User registeredUser = userService.registerUser(user);
            VerificationToken token = verificationService.createToken(registeredUser);
            verificationService.sentTokenToUser(token);

            log.info("registered: " + user);
        } catch (EmailExistException e) {
            log.info("user exists: " + user);
            FieldError emailError = new FieldError("user", "email", e.getMessage());
            result.addError(emailError);
            return new ModelAndView("registrationPage", "user", user);
        }

        return new ModelAndView("redirect:/login");
    }
}
