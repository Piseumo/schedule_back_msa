package com.example.userservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserResponseDto {

    @Schema(hidden = true)
    private final Long idx;
}