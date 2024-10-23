package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {
    private Integer userId;
    private String address;
    private String phoneNumber;


}
