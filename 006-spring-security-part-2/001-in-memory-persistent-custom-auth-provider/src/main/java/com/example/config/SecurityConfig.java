package com.example.config;

import com.example.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("1").authorities("ROLE_USER");


//        DaoAuthenticationProvider blankDaoProvider = new DaoAuthenticationProvider();
//        auth.authenticationProvider(blankDaoProvider);

        auth.authenticationProvider(authenticationProvider);

//        ProviderManager provider = new ProviderManager(blankDaoProvider);
//        auth.parentAuthenticationManager(provider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter: off
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
        // @formatter: on
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
