package com.company.ziplink.controllers;

import com.company.ziplink.DTOs.UserPostRequestDTO;
import com.company.ziplink.DTOs.UserPostResponseDTO;
import com.company.ziplink.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UserController.class)
public class UserControllerTest {

    private final ObjectMapper objectMapper = new JsonMapper().builder().findAndAddModules().build();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Must return status 201 when user is created successfully")
    void testCreateUser() throws Exception {
        UserPostRequestDTO fakeRequest = new UserPostRequestDTO("John Doe", "john.doe@example.com", "Testing123");

        when(userService.createUser(any(UserPostRequestDTO.class))).thenReturn(new UserPostResponseDTO(
                UUID.randomUUID(),
                "John Doe",
                "john.doe@example.com",
                LocalDateTime.now()
        ));

        MvcResult result = mockMvc.perform(post("/create-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fakeRequest))
                        .with(user("Test").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        UserPostResponseDTO response = objectMapper.readValue(json, UserPostResponseDTO.class);

        assertThat(response).isNotNull();
        assertThat(response.id()).isNotNull();
        assertThat(response.name()).isEqualTo("John Doe");
        assertThat(response.email()).isEqualTo("john.doe@example.com");
        assertThat(response.createdAt()).isNotNull();
    }
}
