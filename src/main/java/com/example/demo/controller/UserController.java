package com.example.demo.controller;


import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserProfileDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
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

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
         UserDTO dto = userService.createUser(user);
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

    @GetMapping("/profiles")
    public List<UserProfileDTO> getAllUsersWithProfiles() {
        return userService.getAllUsersWithProfiles();
    }
}
