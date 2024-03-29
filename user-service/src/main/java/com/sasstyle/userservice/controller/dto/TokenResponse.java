package com.sasstyle.userservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "로그인 응답 DTO")
@Getter
@AllArgsConstructor
public class TokenResponse {

    @Schema(description = "액세스 토큰", required = true)
    private String accessToken;

    @Schema(description = "리프레시 토큰", required = true)
    private String refreshToken;
}
