package com.example.friendservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notification-service", url = "${notification-service-url}")
public interface NotiFeignClient {

    //친구 요청 알림
    @GetMapping(value = "/noti/friend-request")
    ResponseEntity<Void> friendRequest(@RequestParam String userName, @RequestParam String friendName);

//    //친구 수락 알림
//    @GetMapping(value = "/noti/friend-accept/{receiverId}/{requestId}")
//    ResponseEntity<Void> friendAccept(@PathVariable(value = "receiverId") Long receiverId, @PathVariable(value = "requestId") Long requestId);


}
