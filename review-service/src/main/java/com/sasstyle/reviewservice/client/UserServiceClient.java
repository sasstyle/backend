package com.sasstyle.reviewservice.client;

import com.sasstyle.reviewservice.client.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/users/{userId}")
    UserResponse findByUserId(@PathVariable String userId);
}
