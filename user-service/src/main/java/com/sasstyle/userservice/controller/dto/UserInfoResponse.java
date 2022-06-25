package com.sasstyle.userservice.controller.dto;

import com.sasstyle.userservice.entity.Gender;
import com.sasstyle.userservice.entity.Role;
import com.sasstyle.userservice.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponse {

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "역할", example = "일반")
    private String role;

    @Schema(description = "성별", example = "MAN")
    private Gender gender;

    @Schema(description = "이메일", example = "sasstyle@sasstyle.com")
    private String email;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNumber;

    @Schema(description = "주소", example = "서울시 어딘가...")
    private String address;

    public UserInfoResponse(User user) {
        this.name = user.getName();
        this.role = user.getRole().getDescription();
        this.gender = user.getGender();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress().getDetails();
    }
}
