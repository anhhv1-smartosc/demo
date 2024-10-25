package com.example.demo.service;

import com.example.demo.dto.response.RoleResponse;
import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.repository.PermissionRepo;
import com.example.demo.repository.RoleRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionRepo permissionRepo;


    public RoleResponse create(Role role) {
        roleRepo.save(role);
        log.info(role.toString());
        return roleMapper.toRoleResponse(role);
    }


    public List<RoleResponse> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName());
        authentication.getAuthorities().forEach(authority -> log.info(authority.getAuthority()));
        List<RoleResponse> responses = roleRepo.findAll()
               .stream().map(role -> roleMapper.toRoleResponse(role))
               .toList();

       return responses;
    }

    public void delete(String name) {
        roleRepo.deleteById(name);
    }

}
