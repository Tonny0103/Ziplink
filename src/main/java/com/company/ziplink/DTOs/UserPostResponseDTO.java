package com.company.ziplink.DTOs;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public record UserPostResponseDTO(
        UUID id,
        String name,
        String email,
        LocalDateTime createdAt
) {
}
