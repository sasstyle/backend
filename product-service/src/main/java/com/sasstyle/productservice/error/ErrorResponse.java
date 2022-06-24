package com.sasstyle.productservice.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final int responseCode;
    private final String message;
}
