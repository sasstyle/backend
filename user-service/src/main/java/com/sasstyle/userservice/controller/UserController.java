package com.sasstyle.userservice.controller;

import com.sasstyle.userservice.controller.dto.JoinRequest;
import com.sasstyle.userservice.controller.dto.JoinResponse;
import com.sasstyle.userservice.controller.dto.LoginRequest;
import com.sasstyle.userservice.controller.dto.TokenResponse;
import com.sasstyle.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그인", description = "아이디와 비밀번호를 이용하여 로그인을 진행합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity
                .ok(userService.login(request));
    }

    @Operation(summary = "회원가입", description = "새로운 사용자를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 사용자 아이디 혹은 이메일로 회원가입을 진행하는 경우 발생할 수 있습니다.")
    })
    @PostMapping
    public ResponseEntity<JoinResponse> join(@RequestBody JoinRequest request) {
        return ResponseEntity
                .status(CREATED)
                .body(userService.create(request));
    }

    @Operation(summary = "서버 체크", description = "서버가 정상적으로 실행되는지 확인합니다.")
    @ApiResponse(responseCode = "200", description = "서버 체크 성공")
    @GetMapping("/health")
    private ResponseEntity<String> health(HttpServletRequest request) {
        return ResponseEntity
                .ok("현재 서버가 정상적으로 실행됐습니다. 현재 서버의 포트: " +  request.getServerPort() + " 입니다.");
    }
}
