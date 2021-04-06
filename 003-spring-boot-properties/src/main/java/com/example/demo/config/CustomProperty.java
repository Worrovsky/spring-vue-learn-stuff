package com.example.demo.config;

import org.springframework.core.env.PropertySource;

public class CustomProperty extends PropertySource<String> {

    public CustomProperty() {
        super("some prefix");
    }

    @Override
    public Object getProperty(String name) {
        if (name.equalsIgnoreCase("custom-property")) {
            return "value for custom property";
        }

        return null;
    }
}
