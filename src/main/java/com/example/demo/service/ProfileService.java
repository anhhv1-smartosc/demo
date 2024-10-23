package com.example.demo.service;

import com.example.demo.dto.request.ProfileRequest;
import com.example.demo.dto.response.UserProfileDTO;
import com.example.demo.entity.Profile;
import com.example.demo.entity.User;
import com.example.demo.mapper.ProfileMapper;
import com.example.demo.repository.ProfileRepo;
import com.example.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepo profileRepository;

    @Autowired
    private UserRepo userRepository;

    private ProfileMapper profileMapper;

    public UserProfileDTO createProfile(ProfileRequest profileRequestDTO) {

        User user = userRepository.findById(profileRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = new Profile();
        profile.setUser(user);
        profile.setAddress(profileRequestDTO.getAddress());
        profile.setPhoneNumber(profileRequestDTO.getPhoneNumber());

        profileRepository.save(profile);
        return profileMapper.toProfileDTO(profile);

    }
}
