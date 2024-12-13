package com.example.dailyLog.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRequestUpdateDto {

    @Schema(hidden = true)
    private Long idx;

    @NotEmpty
    private String userName;

}
