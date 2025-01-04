package com.example.friendservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "message", url = "${messages-service-url}")
public interface MessageFeignClient {

    @DeleteMapping("message/{userId}/{friendId}")
    ResponseEntity<String> deleteMessage(@PathVariable Long userId, @PathVariable Long friendId);
}
