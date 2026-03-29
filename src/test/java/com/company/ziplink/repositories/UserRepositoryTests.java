package com.company.ziplink.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void mustSaveAndRetrieveUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("test@gmail.com");
        user.setPassword("test123");

        userRepository.save(user);

        Optional<User> founded = userRepository.findByName("John Doe");
        assertThat(founded).isPresent();
        assertThat(founded.get().getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    void mustReturnEmptyForNonExisentUser() {
        Optional<User> result = userRepository.findByName("Doesn't Exists");
        assertThat(result).isEmpty();
    }
}
