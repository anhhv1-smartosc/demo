package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private final UserRepo userRepository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDTO getUserById(Integer id) {
        User u = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.toDTO(u);
    }

    public List<UserDTO> getUserByEmail(String email) {
        List<UserDTO> list = userRepository.findUserByEmail(email)
                .stream()
                .map(user -> mapper.toDTO(user))
                .toList();
        return list;
    }

    public UserDTO createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User user1 =  userRepository.save(user);
        return mapper.toDTO(user1);
    }

    public UserDTO updateUser(Integer id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        user.setEmail(userDetails.getEmail());
        return mapper.toDTO(userRepository.save(user));
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
