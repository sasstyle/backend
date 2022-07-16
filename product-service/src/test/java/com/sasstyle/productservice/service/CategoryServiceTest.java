package com.sasstyle.productservice.service;

import com.sasstyle.productservice.CategoryDummy;
import com.sasstyle.productservice.controller.dto.CategoryResponse;
import com.sasstyle.productservice.entity.Category;
import com.sasstyle.productservice.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private Category 의류 = CategoryDummy.의류();
    private Category 상의 = CategoryDummy.상의();

    @BeforeEach
    void setUp() {
        의류 = CategoryDummy.의류();
        상의 = CategoryDummy.상의();
    }

    @Test
    @DisplayName("모든 카테고리 조회")
    void 모든_카테고리_조회() {
        List<Category> categories = List.of(의류, 상의);
        List<CategoryResponse> responses = categories
                .stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());

        given(categoryRepository.findAllWithChildren()).willReturn(responses);

        List<CategoryResponse> findCategories = categoryService.findAllWithChildren();

        assertThat(findCategories.size()).isEqualTo(categories.size());
    }

    @Test
    @DisplayName("단일 카테고리 조회")
    void 카테고리_조회() {
        상의.getChildren().clear();
        given(categoryRepository.findById(상의.getId())).willReturn(Optional.of(상의));

        Category findCategory = categoryService.findById(상의.getId());

        assertThat(findCategory.getId()).isEqualTo(상의.getId());
        assertThat(findCategory.getName()).isEqualTo(상의.getName());
        assertThat(findCategory.getDepth()).isEqualTo(상의.getDepth());
        assertThat(findCategory.getChildren().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("현재 카테고리 아이디 및 하위 아이디 조회")
    void 카테고리_아이디_조회() {
        given(categoryRepository.findCategoryIds(의류.getId())).willReturn(List.of(상의.getId(), 의류.getId()));

        List<Long> categoryIds = categoryService.findCategoryIds(의류.getId());

        assertThat(categoryIds.size()).isEqualTo(2);
        assertThat(categoryIds).contains(의류.getId());
        assertThat(categoryIds).contains(상의.getId());
    }

    @Test
    @DisplayName("카테고리 생성")
    void 카테고리_생성() {
        given(categoryRepository.save(any())).willReturn(의류);

        Long savedCategoryId = categoryService.createCategory(null, 의류.getName());

        assertThat(savedCategoryId).isEqualTo(의류.getId());
    }

    @Test
    @DisplayName("카테고리 삭제")
    void 카테고리_삭제() {
        given(categoryRepository.findById(의류.getId())).willReturn(Optional.of(의류));

        categoryService.deleteCategory(의류.getId());

        given(categoryRepository.findById(의류.getId())).willReturn(Optional.empty());

        assertThatThrownBy(() -> {
            categoryService.findById(의류.getId());
        }).isInstanceOf(NoSuchElementException.class);
    }

}