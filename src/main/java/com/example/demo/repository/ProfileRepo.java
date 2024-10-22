package com.example.demo.repository;

import com.example.demo.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepo extends JpaRepository<Profile, Integer> {
}
