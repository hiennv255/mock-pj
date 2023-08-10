package com.vti.mockpj.controller;

import com.vti.mockpj.dto.DeviceInfo;
import com.vti.mockpj.dto.LogOutAllDeviceRequest;
import com.vti.mockpj.dto.LoginForm;
import com.vti.mockpj.models.RefreshToken;
import com.vti.mockpj.models.ResponseObject;
import com.vti.mockpj.models.User;
import com.vti.mockpj.models.UserDevice;
import com.vti.mockpj.models.roles.ERole;
import com.vti.mockpj.models.roles.Role;
import com.vti.mockpj.payload.request.SignupRequest;
import com.vti.mockpj.payload.response.JwtResponse2;
import com.vti.mockpj.repository.RoleRepository;
import com.vti.mockpj.repository.UserRepository;
import com.vti.mockpj.security.jwt.JwtProvider;
import com.vti.mockpj.service.RefreshTokenService;
import com.vti.mockpj.service.UserDetailsServiceImpl;
import com.vti.mockpj.service.UserDeviceService;
import com.vti.mockpj.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = {"*","http://localhost:8081",
        "http://localhost:3000","http://localhost:8080",
        "http://localhost:3001"},maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private UserDeviceService userDeviceService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired

    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest
                                          ) {
        if (userRepository.existsByUserName(signUpRequest.getUsername())) {
            System.out.println("AAA2");

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(400,"User already exist ", ""));
        }
        System.out.println("AAA3");

        // Create new user's account
        User user = new User();
        user.setEmail(signUpRequest.getUsername());
        user.setFullName(signUpRequest.getFullName());
        user.setUserName(signUpRequest.getUsername());
        user.setPassWord(encoder.encode(signUpRequest.getPassword()));
        System.out.println("AAA4");

        user.setParentID(1);
        Set<Role> roles = new HashSet<>();
        //Default role is user
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);

        System.out.println("AAA5");

        user.setCreatedTime(new Date());
        user.setModifiedDate(new Date());
        user.setRoles(roles);
        user.setActive(true);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200,"User registered successfully!", userRepository.save(user)));
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginForm loginForm) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginForm.getUsername());

        User u = userService.findUserByUserName(loginForm.getUsername());

        if(encoder.matches(loginForm.getPassword(), userDetails.getPassword())) {
            String token = jwtProvider.generateTokenFromUser(u);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
