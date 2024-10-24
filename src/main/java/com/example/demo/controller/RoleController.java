package com.example.demo.controller;

import com.example.demo.dto.response.RoleResponse;
import com.example.demo.entity.Role;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@RequestBody Role role) {
        RoleResponse response = roleService.create(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAll() {

       return ResponseEntity.ok(roleService.getAll());
    }


    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable int id) {
        roleService.delete(id);

        return ResponseEntity.accepted().body("Delete success");
    }

}
