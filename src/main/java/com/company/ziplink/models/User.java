package com.company.ziplink.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", columnDefinition = "text")
    private String name;

    @Column(name = "email", columnDefinition = "text", unique = true)
    private String email;

    @Column(name = "password", columnDefinition = "text", unique = true)
    private String password;

    @Column(name = "created_at", columnDefinition = "timestamp", insertable = false, updatable = false)
    private Date createdAt;
}
