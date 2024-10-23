package com.example.demo.service;

import com.example.demo.dto.request.JwtRequest;
import com.example.demo.dto.response.UserDTO;
import com.example.demo.dto.response.UserProfileDTO;
import com.example.demo.entity.Profile;
import com.example.demo.entity.User;
import com.example.demo.eums.Role;
import com.example.demo.mapper.ProfileMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepo;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
public class UserService {
    @Autowired
    private final UserRepo userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    protected String SIGNER_KEY = "1TjXchw5FloESb63Kc+DFhTARvpWL4jUGCwfGWxuG5SIf/1y/LgJxHnMqaF6A/ij";

    public UserService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDTO getUserById(Integer id) {
        User u = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(u);
    }

    public List<UserDTO> getUserByEmail(String email) {
        List<UserDTO> list = userRepository.findUserByEmail(email)
                .stream()
                .map(user -> userMapper.toDTO(user))
                .toList();
        return list;
    }

    public UserDTO createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<String> roles = user.getRoles();

        user.setRoles(roles);
        User user1 = userRepository.save(user);
        return userMapper.toDTO(user1);
    }

    private Role convertToRoleEnum(String role) {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public UserDTO updateUser(Integer id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        user.setEmail(userDetails.getEmail());
        return userMapper.toDTO(userRepository.save(user));
    }

    public List<UserProfileDTO> getAllUsersWithProfiles() {
        List<Profile> profiles = userRepository.findAllUserProfiles();
        return profiles.stream().map(profile -> profileMapper.toProfileDTO(profile))
                .toList();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public String authenticate(JwtRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User u = userRepository.findByUsername(request.getUsername());
        if (u == null) throw new RuntimeException("User not found");

        boolean authenticated = passwordEncoder.matches(request.getPassword(), u.getPassword());

        if (!authenticated) throw new RuntimeException("Not authenticated");
        else {
            String token = generateToken(u);
            return token;
        }
    }

    public String generateToken(User user) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
//                .issuer("devteria.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    public boolean verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!verified) {
            return false;
        }

        Date currentTime = new Date();
        if (expiryTime != null && expiryTime.before(currentTime)) {
            return false;
        }

        return true;

    }

    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(stringJoiner::add);

        return stringJoiner.toString();
    }


}
