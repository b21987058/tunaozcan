package com.example.internshipfinal.controller;

import com.example.internshipfinal.model.User;
import com.example.internshipfinal.request.LoginRequest;
import com.example.internshipfinal.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public boolean login(@RequestBody LoginRequest loginRequest) {

        User auth = loginService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        return auth != null;
    }

}
