package com.example.registration.service;

import com.example.persistent.UserRepository;
import com.example.registration.model.User;
import com.example.registration.validation.EmailExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) throws EmailExistException {
        if (emailExists(user.getEmail())) {
            throw new EmailExistException("Email exists " + user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    private boolean emailExists(String email) {
        User user = repository.findByEmail(email);
        return user != null;
    }
}
