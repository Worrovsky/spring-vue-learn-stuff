package com.example.p007liquibasedemo.service;

import com.example.p007liquibasedemo.domain.User;
import com.example.p007liquibasedemo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAllUsers() {
        return repository.findAll();
    }

    public User save(User user) {
        return repository.save(user);
    }
}
