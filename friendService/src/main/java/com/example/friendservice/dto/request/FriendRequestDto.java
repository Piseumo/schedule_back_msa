package com.example.friendservice.dto.request;

import lombok.Data;

@Data
public class FriendRequestDto {
    Long userId;
    String userName;

    Long friendId;
    String friendName;
}
