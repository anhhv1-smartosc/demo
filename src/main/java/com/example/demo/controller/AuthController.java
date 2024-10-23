package com.example.demo.controller;

import com.example.demo.dto.request.JwtRequest;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.security.UserDetailServiceImpl;
import com.example.demo.service.UserService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public JwtResponse createToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        String token = userService.authenticate(jwtRequest);
        return new JwtResponse(token);
    }

    @GetMapping("/verify-token")
    public ResponseEntity<Boolean> verifyToken(@RequestParam String token) throws ParseException, JOSEException {
        boolean result = userService.verifyToken(token);

        if (result)
            return ResponseEntity.ok(true);
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);

    }

}
