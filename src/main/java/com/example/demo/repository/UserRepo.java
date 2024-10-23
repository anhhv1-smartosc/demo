package com.example.demo.repository;

import com.example.demo.dto.response.UserProfileDTO;
import com.example.demo.entity.Profile;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.email LIKE %?1%")
    public List<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    public User findByUsername(String username);

    @Query(value = "SELECT u.id AS id, u.username AS account_name, p.address AS address, p.phone_number AS phone_number " +
            "FROM users u " +
            "LEFT JOIN profiles p ON u.id = p.user_id",
            nativeQuery = true)
    List<Profile> findAllUserProfiles();
}
