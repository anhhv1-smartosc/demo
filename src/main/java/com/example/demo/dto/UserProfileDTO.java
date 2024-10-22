package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileDTO {
    private Long userId;
    private String username;
    private String address;
    private String phoneNumber;

    public UserProfileDTO(Long userId, String username, String address, String phoneNumber) {
        this.userId = userId;
        this.username = username;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

}
