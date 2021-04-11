package com.example.config;

import com.example.config.beans.SuperService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ConfigComponentScan {

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public SuperService getSuperService() {
        return new SuperService();
    }
}
