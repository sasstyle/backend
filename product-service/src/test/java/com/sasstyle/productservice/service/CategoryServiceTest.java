package com.sasstyle.productservice.service;

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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private List<CategoryResponse> categories = new ArrayList<>();

    private Category 의류;
    private Category 상의;
    private Category 맨투맨;
    private Category 후드티셔츠;

    @BeforeEach
    void setUp() {
        의류 = new Category(1L, null,"의류", 1, new ArrayList<>(), new ArrayList<>());
        상의 = new Category(2L, 의류,"상의", 2, new ArrayList<>(), new ArrayList<>());
        맨투맨 = new Category(3L, 상의,"맨투맨", 3, new ArrayList<>(), new ArrayList<>());
        후드티셔츠 = new Category(4L, 상의,"후드티셔츠", 3, new ArrayList<>(), new ArrayList<>());

        의류.addChildren(상의);
        상의.addChildren(맨투맨);
        상의.addChildren(후드티셔츠);

        categories = Arrays.asList(new CategoryResponse(의류),
                new CategoryResponse(상의),
                new CategoryResponse(맨투맨),
                new CategoryResponse(후드티셔츠));
    }

    @Test
    @DisplayName("모든 카테고리 조회")
    void 모든_카테고리_조회() {
        given(categoryRepository.findAllWithChildren()).willReturn(categories);

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
        given(categoryRepository.findCategoryIds(상의.getId())).willReturn(List.of(상의.getId(), 맨투맨.getId(), 후드티셔츠.getId()));

        List<Long> categoryIds = categoryService.findCategoryIds(상의.getId());

        assertThat(categoryIds.size()).isEqualTo(3);
        assertThat(categoryIds).contains(상의.getId());
        assertThat(categoryIds).contains(맨투맨.getId());
        assertThat(categoryIds).contains(후드티셔츠.getId());
    }

    @Test
    @DisplayName("카테고리 생성")
    void 카테고리_생성() {
        Category 신발 = dummyCategory();

        given(categoryRepository.save(any())).willReturn(신발);

        Long savedCategoryId = categoryService.createCategory(null, 신발.getName());

        assertThat(savedCategoryId).isEqualTo(신발.getId());
    }

    @Test
    @DisplayName("카테고리 삭제")
    void 카테고리_삭제() {
        given(categoryRepository.findById(상의.getId())).willReturn(Optional.of(상의));

        categoryService.deleteCategory(상의.getId());

        given(categoryRepository.findById(상의.getId())).willReturn(Optional.empty());

        assertThatThrownBy(() -> {
            categoryService.findById(상의.getId());
        }).isInstanceOf(NoSuchElementException.class);
    }

    private Category dummyCategory() {
        return new Category(1L, null, "신발", 0, new ArrayList<>(), new ArrayList<>());
    }
}