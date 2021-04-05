package com.example.demo;

import com.example.demo.config.EmailProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    @Value("${nameFromProperties}")
    private String name;
    @Value("${userId}")
    private String userId;
    @Value("${isConfirmed}")
    private boolean isConfirmed;
    @Value("${age}")
    private int age;

    private final EmailProps emailProps;

    @Autowired
    public Runner(EmailProps emailProps) {
        this.emailProps = emailProps;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello, " + name);
        System.out.println("Your id: " + userId);
        System.out.println("Your are confirmed: " + isConfirmed);
        System.out.println("You age: " + age);

        System.out.println(emailProps);
    }

    private void sayFavoriteColor(String color) {
        System.out.println("My favorite color is " + color);
    }
}
