package com.company.ziplink.controllers;

import com.company.ziplink.DTOs.UserPostRequestDTO;
import com.company.ziplink.DTOs.UserPostResponseDTO;
import com.company.ziplink.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<UserPostResponseDTO> createUser(@RequestBody UserPostRequestDTO user) {
        UserPostResponseDTO response = userService.createUser(user);
        return ResponseEntity.status(201).body(response);
    }
}
