package com.example.demo.dto.response;


import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    private int id;
    private String userAccount;
    private String passcode;
    private String email;
    private Set<String> roles;
}
