package com.example.protectedData;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedController {

    @GetMapping("/secret")
    public String secret() {
        return "You got secret!";
    }
}
