package com.example.notificationService.service;


import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.lang.reflect.Member;

public interface NotificationService {

    String sendFriendRequest(String userName, String friendName);
    void sendFriendAccept(String friendName, String userName);
    void sendMessage(String username);
    void sendComment(String username);
    void sendFriendPost(String username);

}


