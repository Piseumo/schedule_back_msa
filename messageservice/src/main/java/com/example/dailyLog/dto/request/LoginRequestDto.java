package com.example.dailyLog.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Email(message = "이메일 형식을 지켜주세요.")
    @Schema(example = "aaa@naver.com")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&])[a-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 8자 이상이어야 하며, 영문 대문자, 소문자, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다.")
    @Schema(example = "p@ssword123")
    private String password;
}