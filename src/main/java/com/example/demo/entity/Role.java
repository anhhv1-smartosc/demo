package com.example.demo.entity;

import com.example.demo.eums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;

//    @Enumerated(EnumType.STRING)
    @Id
    private String name;

    private String description;

    @ManyToMany
    Set<Permission> permissions;

}
