package com.sasstyle.userservice;

import com.sasstyle.userservice.controller.dto.JoinRequest;
import com.sasstyle.userservice.controller.dto.RoleRequest;
import com.sasstyle.userservice.controller.dto.UserUpdateRequest;
import com.sasstyle.userservice.entity.Gender;
import com.sasstyle.userservice.entity.Role;
import com.sasstyle.userservice.entity.User;
import com.sasstyle.userservice.entity.UserProfile;

public class UserDummy {

    public static User user() {
        UserProfile userProfile = UserProfile.builder()
                .profileUrl("https://picsum.photos/seed/picsum/200/300")
                .build();

        return User.builder()
                .userProfile(userProfile)
                .userId("02a5c76e-fa26-4ea6-a797-0756a09e7f76")
                .username("sasstyle")
                .password("test1234!")
                .name("이순신")
                .role(Role.USER)
                .gender(Gender.MAN)
                .email("lee@example.com")
                .phoneNumber("010-1234-5678")
                .address("서울시 어딘가...")
                .build();
    }

    public static JoinRequest join() {
        return new JoinRequest(
                "https://picsum.photos/seed/picsum/200/300",
                "sasstyle",
                "test1234!",
                "이순신",
                Gender.MAN,
                "lee@example.com",
                "010-1234-5678",
                "서울시 어딘가...",
                RoleRequest.BRAND
        );
    }

    public static UserUpdateRequest updateRequest() {
        return new UserUpdateRequest(
                "https://picsum.photos/seed/picsum/200/300",
                "test1234!",
                "이순신",
                Gender.MAN,
                "lee@example.com",
                "010-1234-5678",
                "서울시 어딘가..."
        );
    }
}
