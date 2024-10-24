package com.example.demo.controller;

import com.example.demo.dto.request.ProfileRequest;
import com.example.demo.dto.response.UserProfileDTO;
import com.example.demo.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<UserProfileDTO> createProfile(@RequestBody ProfileRequest profileRequestDTO) {
        return ResponseEntity.accepted().body(profileService.createProfile(profileRequestDTO));
    }
}