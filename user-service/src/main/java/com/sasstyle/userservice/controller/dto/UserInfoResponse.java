package com.sasstyle.userservice.controller.dto;

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

    @Schema(description = "프로필 이미지", example = "https://picsum.photos/seed/picsum/200/300", required = true)
    private String profileUrl;

    @Schema(description = "아이디", example = "sasstyle", required = true)
    private String username;

    @Schema(description = "이름", example = "홍길동", required = true)
    private String name;

    @Schema(description = "역할", example = "일반", required = true)
    private String role;

    public UserInfoResponse(User user) {
        this.profileUrl = user.getUserProfile().getProfileUrl();
        this.username = user.getUsername();
        this.name = user.getName();
        this.role = user.getRole().getDescription();
    }
}
