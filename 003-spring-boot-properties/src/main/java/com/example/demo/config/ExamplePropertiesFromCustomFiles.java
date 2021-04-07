package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/customFiles/foo.properties")
@PropertySource("classpath:/customFiles/bar.properties")
public class ExamplePropertiesFromCustomFiles {

    final Environment env;

    public ExamplePropertiesFromCustomFiles(Environment env) {
        this.env = env;
    }

    public Environment getEnv() {
        return env;
    }

    public void sayHello() {
        System.out.println(env.getProperty("foo.message"));
        System.out.println(env.getProperty("bar.message"));
    }

}
