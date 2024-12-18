package com.example.friendservice.feign;

import com.example.friendservice.dto.request.FriendRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notification-service", url = "${notification-service-url")
public interface NotiFeignClient {

    //친구 요청 알림
    @GetMapping(value = "/friend-request")
    ResponseEntity<Void> friendRequest(@RequestParam String userName, String friendName);

    //친구 수락 알림
    @GetMapping(value = "/friend-accept")
    ResponseEntity<Void> friendAccept(@PathVariable Long receiverId, Long requestId);


}
