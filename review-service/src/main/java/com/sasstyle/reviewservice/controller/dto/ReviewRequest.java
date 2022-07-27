package com.sasstyle.reviewservice.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRequest {

    private Long productId;
    private String content;
    private List<String> images;
    private int rate;
}
