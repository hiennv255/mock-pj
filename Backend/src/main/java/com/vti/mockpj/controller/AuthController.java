package com.vti.mockpj.controller;

import com.vti.mockpj.dto.LoginForm;
import com.vti.mockpj.payload.request.SignupRequest;

import com.vti.mockpj.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*","http://localhost:8081",
        "http://localhost:3000","http://localhost:8080",
        "http://localhost:3001"},maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest)
    {
        return authService.registerUser(signUpRequest);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginForm loginForm)
    {
        return authService.loginUser(loginForm);
    }

}
