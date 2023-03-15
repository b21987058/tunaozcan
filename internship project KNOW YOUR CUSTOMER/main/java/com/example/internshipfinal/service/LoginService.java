package com.example.internshipfinal.service;

import com.example.internshipfinal.model.User;
import com.example.internshipfinal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public User authenticate(String username, String password) {

        return userRepository.findByUsernameAndPassword(username, password).orElse(null);

    }

}
