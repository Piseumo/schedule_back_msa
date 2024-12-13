package com.example.dailyLog.dto.request;

import com.example.dailyLog.entity.Calendars;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
public class UserRequestDto {

    @Schema(hidden = true)
    private Long idx;

    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,10}$", message = "닉네임은 영문, 한글, 숫자만 사용할 수 있습니다.")
    @Schema(description = "닉네임 중복 불가")
    private String userName;

    private String Profile;

    private LocalDateTime joinDate;

    @Schema(hidden = true)
    private Calendars calendars;
}
