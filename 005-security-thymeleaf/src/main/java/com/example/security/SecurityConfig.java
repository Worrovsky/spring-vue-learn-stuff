package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
          .authorizeRequests()
            .mvcMatchers("/signup", "/user/register").permitAll()
            .mvcMatchers("person").permitAll()
            .mvcMatchers("/h2/**").permitAll()
            .mvcMatchers("/leaf").permitAll()
            .mvcMatchers("/registrationConfirm/**"
                    , "/forgotPassword"
                    , "/user/changePassword"
                    , "/user/savePassword"
                    , "user/resetPassword").permitAll()
                .mvcMatchers("/login").permitAll() // for testing url with params like /login?foo=bar
            .anyRequest().authenticated()
          .and()
            .formLogin()
              .loginPage("/login").permitAll()
                .loginProcessingUrl("/doLogin")
                  .usernameParameter("custom-username")
          .and()
            .logout().permitAll().logoutUrl("/logout")
          .and()
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .rememberMeCookieName("custom-remember-me-cookie")
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(60*2);

       //formatter:on

        http.csrf().disable();
        http.headers().frameOptions().disable();

    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
