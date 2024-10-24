package com.example.demo.controller;

import com.example.demo.dto.response.PermissionResponse;
import com.example.demo.entity.Permission;
import com.example.demo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService service;

    @PostMapping
    ResponseEntity<PermissionResponse> create(@RequestBody Permission permission) {

        return ResponseEntity.accepted().body(service.create(permission));
    }

    @GetMapping
    ResponseEntity<List<PermissionResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }


    @DeleteMapping("/{permission}")
    ResponseEntity<String> delete(@PathVariable String permission) {
        service.delete(permission);

        return ResponseEntity.accepted().body("Delete success");
    }
}
