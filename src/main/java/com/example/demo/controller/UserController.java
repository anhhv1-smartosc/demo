package com.example.demo.controller;


import com.example.demo.dto.request.UserCreate;
import com.example.demo.dto.response.UserDTO;
import com.example.demo.dto.response.UserProfileDTO;
import com.example.demo.entity.User;
import com.example.demo.service.OtpService;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @GetMapping
    public List<User> getAllUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        UserDTO dto = userService.getUserById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> getUserByEmail(@RequestParam String email) {
        List<UserDTO> userDTOList = userService.getUserByEmail(email);
        return ResponseEntity.ok(userDTOList);
    }

    @PostMapping("/generate-token")
    public ResponseEntity<String> createToken(@RequestBody User user) {
        String token = otpService.generateOtp(user.getUsername());
        return ResponseEntity.ok(token);
    }

//    @PostMapping("/verify-token")
//    public ResponseEntity<Boolean> verifyToken() {
//
//
//        boolean isValid = otpService.verifyOtp(token);
//        if (isValid) {
//            return ResponseEntity.ok(true);
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
//        }
//    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreate request) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
         UserDTO dto = userService.createUser(request);
         return ResponseEntity.accepted().body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody User user) {
        UserDTO updatedUser = userService.updateUser(id, user);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {

        userService.deleteUser(id);
        return ResponseEntity.ok("User has been deleted");
    }


}
