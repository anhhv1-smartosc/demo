package com.example.demo.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserProfileDTO implements Serializable {
    private Integer userId;
    private String username;
    private String address;
    private String phoneNumber;


}
