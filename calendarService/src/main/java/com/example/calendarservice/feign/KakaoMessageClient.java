package com.example.calendarservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao-message-service", url = "https://kapi.kakao.com")
public interface KakaoMessageClient {

    @PostMapping(value = "/v2/api/talk/memo/default/send", consumes = "application/x-www-form-urlencoded;charset=utf-8")
    String sendMessage(
            @RequestHeader("Authorization") String authorization, // Authorization 헤더
            @RequestParam("template_object") String templateObject // 요청 데이터
    );
}
