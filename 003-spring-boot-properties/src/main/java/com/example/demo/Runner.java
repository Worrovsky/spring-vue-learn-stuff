package com.example.demo;

import com.example.demo.config.EmailProps;
import com.example.demo.config.ExamplePropertiesFromCustomFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
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

    @Value("${nameFromProperties} ${userId}")
    private String combinedProperty;

    @Value("${foo.count}")
    int countFromCustomPropertiesFile;

    private final EmailProps emailProps;

    @Autowired
    private Environment env;

    @Autowired
    public Runner(EmailProps emailProps) {
        this.emailProps = emailProps;
    }

    @Autowired
    ExamplePropertiesFromCustomFiles propertiesFromCustomFiles;
    @Override
    public void run(String... args) {
        System.out.println("Hello, " + name);

        System.out.println("Your id: " + userId);
        System.out.println("Your are confirmed: " + isConfirmed);
        System.out.println("You age: " + age);

        System.out.println("Combined: " + combinedProperty);

        System.out.println(emailProps);

        System.out.println("Property from Environment: " + env.getProperty("email.post-index"));

        propertiesFromCustomFiles.sayHello();
        System.out.println("count from custom properties file(foo.properties) " + countFromCustomPropertiesFile);
    }

    private void sayFavoriteColor(String color) {
        System.out.println("My favorite color is " + color);
    }
}
