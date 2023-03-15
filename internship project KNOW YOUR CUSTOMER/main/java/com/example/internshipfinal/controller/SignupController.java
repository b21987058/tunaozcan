package com.example.internshipfinal.controller;

import com.example.internshipfinal.model.User;
import com.example.internshipfinal.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SignupController {

    @Autowired
    SignupService signupService;

    @PostMapping("/signup")
    public boolean signup(@ModelAttribute User user) {

        User signedUpUser = signupService.signup(
                user.getFirstName(),
                user.getLastName(),
                user.getTcNumber(),
                user.getUsername(),
                user.getPassword()
        );


        return signedUpUser != null;
    }

}
