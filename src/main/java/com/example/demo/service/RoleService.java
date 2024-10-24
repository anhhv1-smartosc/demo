package com.example.demo.service;

import com.example.demo.dto.response.RoleResponse;
import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.repository.PermissionRepo;
import com.example.demo.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionRepo permissionRepo;


    public RoleResponse create(Role role) {
        RoleResponse response = roleMapper.toRoleResponse(role);
        String name = role.getName().toString();
        response.setName(name);
        roleRepo.save(role);
        return response;
    }


    public List<RoleResponse> getAll() {
       List<RoleResponse> responses = new ArrayList<>();
       List<Role> roles = roleRepo.findAll();
       for (Role role : roles) {
           RoleResponse dto = roleMapper.toRoleResponse(role);
           dto.setName(role.getName().toString());
           responses.add(dto);
       }
       return responses;
    }

    public void delete(Integer id) {
        roleRepo.deleteById(id);
    }

}
