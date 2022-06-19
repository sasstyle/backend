package com.sasstyle.userservice.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(description = "에러 응답 DTO")
@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    @Schema(description = "상태 코드", example = "400", required = true)
    private final int responseCode;

    @Schema(description = "에러 메시지", example = "아이디가 존재하지 않습니다.")
    private final String message;
}
