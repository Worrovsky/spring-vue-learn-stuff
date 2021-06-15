package com.example.reset;

import com.example.registration.model.User;
import com.example.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private UserService userService;

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/user/resetPassword")
    public ModelAndView resetPassword(@RequestParam("email") String email, HttpServletRequest req,
                                      RedirectAttributes redirectAttributes) {
        User user = userService.findUserByEmail(email);

        if (user != null) {
            PasswordResetToken token = passwordResetService.createResetToken(user);
            passwordResetService.sendTokenToUser(req, token);

            redirectAttributes.addFlashAttribute("message", "Check reset password link");
//            redirectAttributes.addAttribute("foo", "bar");
            return new ModelAndView("redirect:/login");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid email");
            redirectAttributes.addAttribute("foo2", "bar2");
            return new ModelAndView("redirect:/login");
        }
    }

    @GetMapping("user/changePassword")
    public ModelAndView changePassword(@RequestParam("id") Long userId, @RequestParam("token") String token,
                                       RedirectAttributes redirectAttributes) {

        PasswordResetToken resetToken = passwordResetService.getResetToken(token);
        if (resetToken == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid password reset token");
            return new ModelAndView("redirect:/login");
        }

        User user = resetToken.getUser();
        if (!user.getId().equals(userId)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid password reset token");
            return new ModelAndView("redirect:/login");
        }

        if (passwordResetService.isTokenExpired(resetToken)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Password reset token has expired");
            return new ModelAndView("redirect:/login");
        }

        ModelAndView modelAndView = new ModelAndView("resetPassword");
        modelAndView.addObject("token", token);
        return modelAndView;

    }

    @PostMapping("user/savePassword")
    public ModelAndView savePassword(@RequestParam("password") String password
            , @RequestParam("passwordConfirmation") String passwordConfirmation
            , @RequestParam("token") String token
            , RedirectAttributes redirectAttributes) {

        if (!password.equals(passwordConfirmation)) {
            return new ModelAndView("resetPassword", "errorMessage", "password do not match");
        }

        PasswordResetToken passwordToken = passwordResetService.getResetToken(token);
        if (passwordToken == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid token");
        } else {
            User user = passwordToken.getUser();
            if (user == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Unknown user");
            } else {
                userService.changePassword(user, password);
                redirectAttributes.addFlashAttribute("message", "Password reset successfully");
            }
        }
        return new ModelAndView("redirect:/login");
    }


}
