package com.example.demo.dto;


import com.example.demo.eums.Role;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    private int id;
    private String userAccount;
    private String passcode;
    private String email;
    private String role;
}
