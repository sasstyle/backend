package com.sasstyle.userservice.controller;

import com.sasstyle.userservice.controller.dto.JoinRequest;
import com.sasstyle.userservice.controller.dto.JoinResponse;
import com.sasstyle.userservice.controller.dto.LoginRequest;
import com.sasstyle.userservice.security.jwt.TokenResponse;
import com.sasstyle.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity
                .ok(userService.login(request));
    }

    @PostMapping
    public ResponseEntity<JoinResponse> join(@RequestBody JoinRequest request) {
        return ResponseEntity
                .status(CREATED)
                .body(userService.create(request));
    }

    @GetMapping("/health_check")
    private ResponseEntity<String> health_check() {
        return ResponseEntity
                .ok("health ok");
    }
}
