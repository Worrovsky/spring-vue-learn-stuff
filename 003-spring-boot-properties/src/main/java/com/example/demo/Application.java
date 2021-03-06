package com.example.demo;

import com.example.demo.config.CustomProperty;
import com.example.demo.config.EmailProps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

@SpringBootApplication
@EnableConfigurationProperties(EmailProps.class)
public class Application {

    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);

        new SpringApplicationBuilder()
                .sources(Application.class)
                .initializers(new ApplicationContextInitializer<ConfigurableApplicationContext>() {
                    @Override
                    public void initialize(ConfigurableApplicationContext applicationContext) {
                        MutablePropertySources propertySources = applicationContext.getEnvironment().getPropertySources();
                        propertySources.addLast(new CustomProperty());
                    }
                })
                .run(args);


    }

    @Bean
    ApplicationRunner applicationRunner(Environment env,
                                        @Value("${HOME:defaultHomeProperty}") String userHome,
                                        @Value("${custom-property}") String customProperty ) {
        return args -> {
            System.out.println("Hello from Application Runner");
            System.out.println("user home from param: " + userHome );
            String propertyName = "nameFromProperties";
            System.out.println("Property from env: " + env.getProperty(propertyName));

            System.out.println("Custom property: " + customProperty);

            System.out.println("");

            System.out.println("Active profile:");
            String[] activeProfiles = env.getActiveProfiles();
            for (String profile : activeProfiles) {
                System.out.println(profile);
            }

            System.out.println("--------  End --------- ");
        };
    }

}
