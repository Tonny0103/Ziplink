package com.company.ziplink.services;

import com.company.ziplink.DTOs.UserPostRequestDTO;
import com.company.ziplink.DTOs.UserPostResponseDTO;
import com.company.ziplink.models.User;
import com.company.ziplink.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserPostResponseDTO createUser(UserPostRequestDTO user) {
        User userModel = new User(
                null,
                user.name(),
                user.email(),
                user.password(),
                LocalDateTime.now()
        );

        if (userRepository.existsUserByEmail(userModel.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User userCreated = userRepository.save(userModel);

        return new UserPostResponseDTO(
                userCreated.getId(),
                userCreated.getName(),
                userCreated.getEmail(),
                userCreated.getCreatedAt()
        );
    }
}
