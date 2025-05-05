// src/main/java/com/example/model/User.java
package com.example.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    public enum Role {
        USER, ADMIN
    }
}