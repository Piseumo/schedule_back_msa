package com.example.notificationService.service;


import com.example.notificationService.entity.Notification;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.lang.reflect.Member;
import java.util.List;

public interface NotificationService {

    String sendFriendRequest(String userName, String friendName);
    void sendFriendAccept(String friendName, String userName);
    List<Notification> getUnreadNotifications(String userName);
    void sendMessage(String username);
    void sendComment(String username);
    void sendFriendPost(String username);

}


