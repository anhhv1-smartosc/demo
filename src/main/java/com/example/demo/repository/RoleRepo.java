package com.example.demo.repository;

import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

    @Query(value = "SELECT * FROM role r WHERE r.name = ?1", nativeQuery = true)
    Role findByName(String name);
}
