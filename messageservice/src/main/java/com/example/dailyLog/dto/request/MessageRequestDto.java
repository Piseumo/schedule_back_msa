package com.example.dailyLog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageRequestDto {

    @NotNull(message = "Sender ID는 필수 값입니다.")
    private Long senderId;

    @NotNull(message = "Receiver ID는 필수 값입니다.")
    private Long receiverId;

    @NotBlank(message = "메시지 내용은 비어 있을 수 없습니다.")
    @Size(max = 1000, message = "메시지 내용은 최대 1000자까지 입력 가능합니다.")
    private String content;
}