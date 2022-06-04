package com.sasstyle.userservice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MAN("남자"), WOMAN("여자");

    private final String description;
}
