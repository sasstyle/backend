package com.sasstyle.userservice.controller;

import com.sasstyle.userservice.controller.dto.*;
import com.sasstyle.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "내 정보 조회", description = "인증된 사용자에 해당하는 정보를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 정보 조회 성공"),
            @ApiResponse(responseCode = "401", description = "로그인이 안 되어 있는 경우 발생할 수 있습니다.")
    })
    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> myInfo(@RequestHeader String userId) {
        log.info("userId = {}", userId);

        return ResponseEntity
                .ok(new UserInfoResponse(userService.findByUserId(userId)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoResponse> findByUserId(@PathVariable String userId) {
        return ResponseEntity
                .ok(new UserInfoResponse(userService.findByUserId(userId)));
    }

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
    public ResponseEntity<JoinResponse> join(@Validated @RequestBody JoinRequest request) {
        return ResponseEntity
                .status(CREATED)
                .body(userService.createUser(request));
    }

    @Operation(summary = "회원정보 수정", description = "사용자 계정의 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원정보 수정 성공"),
            @ApiResponse(responseCode = "401", description = "로그인이 안 되어 있는 경우 발생할 수 있습니다.")
    })
    @PutMapping
    public ResponseEntity<UserInfoResponse> updateUser(@RequestHeader String userId, @Validated @RequestBody UserUpdateRequest request) {
        return ResponseEntity
                .ok(new UserInfoResponse(userService.updateUser(userId, request)));
    }

    @Operation(summary = "회원탈퇴", description = "사용자 계정의 정보를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원탈퇴 성공"),
            @ApiResponse(responseCode = "401", description = "로그인이 안 되어 있는 경우 발생할 수 있습니다.")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestHeader String userId) {
        userService.deleteUser(userId);

        return ResponseEntity
                .ok()
                .build();
    }

}
