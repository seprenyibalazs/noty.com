package com.noty.web.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String email;

    private String token;

    private Date createdAt;

    public User(String email, String token, Date createdAt) {
        this.email = email;
        this.token = token;
        this.createdAt = createdAt;
    }
}
