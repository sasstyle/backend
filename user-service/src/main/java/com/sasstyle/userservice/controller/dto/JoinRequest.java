package com.sasstyle.userservice.controller.dto;

import com.sasstyle.userservice.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "회원가입 요청 DTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {

    @Schema(description = "아이디", example = "sasstyle", required = true)
    private String username;

    @Schema(description = "비밀번호", example = "test1234!", required = true)
    private String password;

    @Schema(description = "이름", example = "이순신", required = true)
    private String name;

    @Schema(description = "성별", example = "남자", required = true)
    private Gender gender;

    @Schema(description = "이메일", example = "lee@example.com", required = true)
    private String email;

    @Schema(description = "전화번호", example = "010-1234-5678", required = true)
    private String phoneNumber;

    // Address
    @Schema(description = "주소", example = "서울시 어딘가...")
    private String address;
}
