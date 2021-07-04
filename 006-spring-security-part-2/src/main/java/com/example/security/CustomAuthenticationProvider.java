package com.example.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        // in real app here should check credentials, may be with third part system

        Authentication result = new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
        return result;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
