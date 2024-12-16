package com.example.notificationService.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDto {
    private String username;
    private String eventType;
    private String message;
}
