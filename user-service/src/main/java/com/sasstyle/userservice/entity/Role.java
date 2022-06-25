package com.sasstyle.userservice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("일반"), BRAND("브랜드"), ADMIN("관리자");

    private static final String PREFIX = "ROLE_";
    private final String description;

    public String getFullName() {
        return PREFIX + name();
    }
}
