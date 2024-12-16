package com.example.friendservice.service;


import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.lang.reflect.Member;

public interface NotificationService {

    SseEmitter subscribe(String member, String lastEventId);

}
