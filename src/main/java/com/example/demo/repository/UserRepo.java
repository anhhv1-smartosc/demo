package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.email LIKE %?1%")
    public List<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    public User findByUsername(String username);
}
