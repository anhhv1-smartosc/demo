package com.example.demo.service;

import com.example.demo.dto.response.PermissionResponse;
import com.example.demo.entity.Permission;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.repository.PermissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepo repo;

    @Autowired
    private PermissionMapper mapper;

    public PermissionResponse create(Permission permission) {
        return mapper.toPermissionResponse(repo.save(permission));
    }

    public List<PermissionResponse> getAll() {
        return repo.findAll().stream()
                .map(permission -> mapper.toPermissionResponse(permission))
                .toList();
    }

    public void delete(String permission) {
        repo.deleteById(permission);
    }
}
