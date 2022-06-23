package com.sasstyle.productservice.controller;

import com.sasstyle.productservice.controller.dto.CategoryResponse;
import com.sasstyle.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> categories() {
        return ResponseEntity
                .ok(categoryService.findAllWithChildren());
    }
}
