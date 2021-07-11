package com.example.service;

import com.example.model.User;
import com.example.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;

public class UserDetailsService003 implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("No such user " + username);
        }

        return new org.springframework.security.core.userdetails.User(user.getName()
                , user.getPassword()
                , true
                , true
                , true
                , true
                , Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
