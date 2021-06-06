package com.example.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
          .authorizeRequests()
            .mvcMatchers("/login", "/signup", "/user/register").permitAll()
            .mvcMatchers("person").permitAll()
            .anyRequest().authenticated()
          .and()
            .formLogin()
              .loginPage("/login")
          .and()
            .logout().permitAll().logoutUrl("/logout");
        //formatter:on

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
