package com.company.ziplink.services;

import com.company.ziplink.models.User;
import com.company.ziplink.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
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
        User fakeUser = fakeUser();
        when(userRepository.existsUserByEmail(fakeUser.getEmail())).thenReturn(false);
        when(userRepository.save(fakeUser)).thenReturn(fakeUser);

        User result = userService.createUser(fakeUser);

        assertNotNull(result);
        assertThat(result.getEmail()).isEqualTo("johndoe67@gmail.com");
        verify(userRepository).save(any(User.class));
    }

    private User fakeUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("John Doe");
        user.setEmail("johndoe67@gmail.com");
        user.setPassword("test123");
        user.setCreatedAt(new Date());
        return user;
    }
}
