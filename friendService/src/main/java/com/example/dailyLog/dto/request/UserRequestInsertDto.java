package com.example.dailyLog.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestInsertDto {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Email(message = "아이디는 이메일 형식입니다.")
    @Schema(example = "aaa@naver.com")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&])[a-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 8자~20자 사이어야 하며, 영문, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다.")
    @Schema(example = "p@ssword123")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Schema(example = "Jason")
    private String userName;

}
