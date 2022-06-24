package com.sasstyle.productservice.controller;

import com.sasstyle.productservice.controller.dto.CategoryIdResponse;
import com.sasstyle.productservice.controller.dto.CategoryRequest;
import com.sasstyle.productservice.controller.dto.CategoryResponse;
import com.sasstyle.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

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

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> category(@PathVariable Long id) {
        return ResponseEntity
                .ok(categoryService.findByIdWithChildren(id));
    }

    @PostMapping
    public ResponseEntity<CategoryIdResponse> createCategory(@RequestBody CategoryRequest request) {
        Long categoryId = categoryService.createCategory(request.getCategoryId(), request.getName());

        return ResponseEntity
                .status(CREATED)
                .body(new CategoryIdResponse(categoryId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);

        return ResponseEntity
                .ok()
                .build();
    }
}
