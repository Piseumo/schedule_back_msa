package com.example.notificationService.service;


import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.lang.reflect.Member;

public interface NotificationService {

    void sendFriendRequest(String username);
    void sendFriendAccept(String username);
    void sendMessage(String username);
    void sendComment(String username);
    void sendFriendPost(String username);


}
