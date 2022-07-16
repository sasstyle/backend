package com.sasstyle.productservice.controller;

import com.sasstyle.productservice.controller.dto.*;
import com.sasstyle.productservice.service.CategoryService;
import com.sasstyle.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @Operation(summary = "모든 카테고리 조회", description = "모든 카테고리를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "모든 카테고리 조회 성공")
    @GetMapping
    public ResponseEntity<Result<List<CategoryResponse>>> categories() {
        return ResponseEntity
                .ok(new Result(categoryService.findAllWithChildren()));
    }

    @Operation(summary = "카테고리 상품 조회", description = "카테고리 아이디 및 카테고리 자식에 해당하는 상품을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 조회 성공"),
            @ApiResponse(responseCode = "400", description = "카테고리 아이디에 해당하는 카테고리가 존재하지 않는 경우에 발생할 수 있습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Page<ProductResponse>> products(@PathVariable Long id, Pageable pageable) {
        List<Long> categoryIds = categoryService.findWithChildrenIds(id);

        return ResponseEntity
                .ok(productService.findAllByCategoryIds(categoryIds, pageable));
    }

    @Operation(summary = "카테고리 생성", description = "상품의 카테고리를 생성합니다.")
    @ApiResponse(responseCode = "201", description = "카테고리 생성 성공")
    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequest request) {
        categoryService.createCategory(request.getCategoryId(), request.getName());

        return ResponseEntity
                .status(CREATED)
                .build();
    }

    @Operation(summary = "카테고리 삭제", description = "카테고리 아이디에 해당하는 카테고리 및 자식 카테고리를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "카테고리 아이디에 해당하는 카테고리가 존재하지 않는 경우에 발생할 수 있습니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);

        return ResponseEntity
                .ok()
                .build();
    }

}
