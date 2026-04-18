package com.company.ziplink.services;

import com.company.ziplink.DTOs.UserPostRequestDTO;
import com.company.ziplink.DTOs.UserPostResponseDTO;
import com.company.ziplink.models.User;
import com.company.ziplink.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void mustCreateUserWithUniqueEmail() {
        UserPostRequestDTO fakeRequest = fakeRequest();
        User fakeUser = fakeUser();

        when(userRepository.existsUserByEmail(fakeRequest.email())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(fakeUser);

        UserPostResponseDTO result = userService.createUser(fakeRequest);

        assertNotNull(result);
        assertThat(result.email()).isEqualTo("john.doe@gmail.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void mustGetUserByName() {
        UserGetFilterDTO filter = new UserGetFilterDTO("John Doe");
        Optional<User> fakeUser = Optional.of(fakeUser());

        when(userRepository.findUserByName(filter.name())).thenReturn(fakeUser);

        userRepository.save(fakeUser);
        Optional<User> result = userService.getUserByName(filter.name());

        assertThat(result).isPresent();
        assertThat(result.get().name()).isEqualTo("John Doe");
    }

    private UserPostRequestDTO fakeRequest() {
        return new UserPostRequestDTO("John Doe", "john.doe@gmail.com", "test123");
    }

    private User fakeUser() {
        return new User(UUID.randomUUID(), "John Doe", "john.doe@gmail.com", "test123", LocalDateTime.now());
    }
}
