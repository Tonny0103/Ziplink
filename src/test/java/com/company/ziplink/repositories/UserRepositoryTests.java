package com.company.ziplink.repositories;

import com.company.ziplink.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void mustReturnAllUsers() {
        List<User> users = usersForFindAllTest();
        userRepository.saveAll(users);

        List<User> result = userRepository.findAll();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(5);
    }

    @Test
    void mustReturnUserIfFoundByName() {
        User user = new User(
                null,
                "John Wick",
                "john.wick@gmail.com",
                "secret123",
                LocalDateTime.now()
        );

        userRepository.save(user);

        Optional<User> result = userRepository.findByName("John Wick");

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("John Wick");
    }

    @Test
    void mustReturnTrueIfUserExistsByEmail() {
        User user = new User(
                null,
                "John Doe",
                "test@gmail.com",
                "secret123",
                LocalDateTime.now()
        );

        userRepository.save(user);

        boolean result = userRepository.existsUserByEmail("test@gmail.com");

        assertThat(result).isTrue();
    }

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

    private List<User> usersForFindAllTest() {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId(null);
            user.setName("John Doe " + i);
            String email = "john.doe" + i + "@gmail.com";
            user.setEmail(email);
            user.setPassword("test123");
            users.add(user);
        }

        return users;
    }
}
