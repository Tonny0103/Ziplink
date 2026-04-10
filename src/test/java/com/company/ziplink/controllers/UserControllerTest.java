package com.company.ziplink.controllers;

import com.company.ziplink.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(UserController.class)
@AutoConfigureRestTestClient
public class UserControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("Must return status 201 when user is created successfully")
    void testCreateUser() throws JsonProcessingException {
        UserDTO user = new UserDTO("John Doe", "john.doe@example.com", "Testing123");
        when(userService.createUser(any(UserDTO.class))).thenReturn(user);

        restTestClient
                .post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(user))
                .exchange()
                .expectStatus().isCreated();
    }
}
