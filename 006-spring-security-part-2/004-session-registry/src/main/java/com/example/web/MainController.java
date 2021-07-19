package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @ResponseBody
    @GetMapping("/")
    public String index() {
        return "Hello, world";
    }

    @ResponseBody
    @GetMapping("/expireSession")
    public String expireSession() {
        return "session expired!";
    }

    @ResponseBody
    @GetMapping("/active")
    public String showActiveUsers() {
        List<Object> principals = sessionRegistry.getAllPrincipals();
        return principals.toString();
    }
}
