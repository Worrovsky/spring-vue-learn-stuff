package com.example.verification;

import com.example.registration.model.User;
import com.example.registration.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(VerificationController.class);

    @GetMapping("/registrationConfirm")
    public ModelAndView confirmRegistration(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {

        log.info("start token verification");

        VerificationToken verificationToken = verificationService.getToken(token);
        if (verificationToken == null) {
            log.info("Invalid token " + token);
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid confirmation token");
            return new ModelAndView("redirect:/login");
        }

        User user = verificationToken.getUser();
        if (verificationService.isTokenExpired(verificationToken)) {
            log.info("Token expired " + token);
            userService.deleteUser(user);
            redirectAttributes.addFlashAttribute("errorMessage", "Token expired. Register again");
            return new ModelAndView("redirect:/login");
        }

        user.setEnabled(true);
        userService.updateUser(user);

        redirectAttributes.addFlashAttribute("message", "Verified successfully!");
        return new ModelAndView("redirect:/login");
    }
}
