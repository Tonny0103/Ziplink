package com.company.ziplink.services;

import com.company.ziplink.models.User;
import com.company.ziplink.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User createUser(User user) {
        if (userRepository.existsUserByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        return userRepository.save(user);
    }
}
