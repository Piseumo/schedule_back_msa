package com.example.friendservice.dto.request;

import lombok.Data;

@Data
public class AcceptFriendRequestDto {
    private Long requesterId;
    private Long receiverId;
}
