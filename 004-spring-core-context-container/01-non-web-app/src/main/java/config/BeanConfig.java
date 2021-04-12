package config;

import config.beans.Greetable;
import config.beans.SpringBeanNo4;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public Greetable getGreetable() {
        return new SpringBeanNo4();


    }
}
