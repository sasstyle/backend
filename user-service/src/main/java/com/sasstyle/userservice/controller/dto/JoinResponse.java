package com.sasstyle.userservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinResponse {

    private Long userId;
    private String username;
}
