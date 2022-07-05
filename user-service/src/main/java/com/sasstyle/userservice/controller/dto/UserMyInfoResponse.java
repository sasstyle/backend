package com.sasstyle.userservice.controller.dto;

import com.sasstyle.userservice.entity.Gender;
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
public class UserMyInfoResponse {

    @Schema(description = "프로필 이미지", example = "https://picsum.photos/seed/picsum/200/300", required = true)
    private String profileUrl;

    @Schema(description = "사용자 UUID", example = "02a5c76e-fa26-4ea6-a797-0756a09e7f76", required = true)
    private String userId;

    @Schema(description = "이름", example = "홍길동", required = true)
    private String name;

    @Schema(description = "역할", example = "일반", required = true)
    private String role;

    @Schema(description = "성별", example = "MAN", required = true)
    private Gender gender;

    @Schema(description = "이메일", example = "sasstyle@sasstyle.com", required = true)
    private String email;

    @Schema(description = "전화번호", example = "010-1234-5678", required = true)
    private String phoneNumber;

    @Schema(description = "주소", example = "서울시 어딘가...", required = true)
    private String address;

    public UserMyInfoResponse(User user) {
        this.profileUrl = user.getUserProfile().getProfileUrl();
        this.userId = user.getUserId();
        this.name = user.getName();
        this.role = user.getRole().getDescription();
        this.gender = user.getGender();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress().getDetails();
    }
}
