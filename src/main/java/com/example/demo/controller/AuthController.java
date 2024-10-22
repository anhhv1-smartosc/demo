package com.example.demo.controller;

import com.example.demo.dto.JwtRequest;
import com.example.demo.dto.JwtResponse;
import com.example.demo.entity.User;
import com.example.demo.security.UserDetailServiceImpl;
import com.example.demo.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.AuthenticationException;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/api/auth/login")
    public JwtResponse createToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword())
            );
        } catch (AuthenticationException ex) {
            throw new Exception("Invalid username or password", ex);
        }

        String token = jwtUtil.generateToken(jwtRequest.getUsername());
        return new JwtResponse(token);
    }
}
