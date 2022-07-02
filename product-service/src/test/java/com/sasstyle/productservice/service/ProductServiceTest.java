package com.sasstyle.productservice.service;

import com.sasstyle.productservice.controller.dto.ProductRequest;
import com.sasstyle.productservice.controller.dto.ProductUpdateRequest;
import com.sasstyle.productservice.entity.Category;
import com.sasstyle.productservice.entity.Product;
import com.sasstyle.productservice.repository.CategoryRepository;
import com.sasstyle.productservice.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    private Category category;
    private ProductRequest request;
    private Product product;

    @BeforeEach
    void setUp() {
        request = new ProductRequest(
                1L,
                "https://picsum.photos/seed/picsum/200/300",
                "한정판 맨투맨",
                10000,
                10,
                "상단 설명",
                "하단 설명",
                new ArrayList<>()
        );

        category = new Category(1L, null,"의류", 1, new ArrayList<>(), new ArrayList<>());
        product = Product.create(category, request);
    }

    @Test
    @DisplayName("상품 등록")
    void 상품_등록() {
        given(categoryRepository.findById(category.getId())).willReturn(Optional.of(category));
        given(productRepository.save(any())).willReturn(product);

        Long productId = productService.createProduct(request);

        assertThat(productId).isEqualTo(product.getId());
    }

    @Test
    @DisplayName("상품 수정")
    void 상품_수정() {
        given(productRepository.findById(any())).willReturn(Optional.of(product));

        ProductUpdateRequest updateRequest = new ProductUpdateRequest(
                "https://picsum.photos/seed/picsum/200/300",
                "예쁜 후드티",
                20000,
                100,
                "업데이트 상단 설명",
                "업데이트 하단 설명");

        productService.updateProduct(product.getId(), updateRequest);

        assertThat(product.getImageUrl()).isEqualTo(updateRequest.getImageUrl());
        assertThat(product.getName()).isEqualTo(updateRequest.getName());
        assertThat(product.getPrice()).isEqualTo(updateRequest.getPrice());
        assertThat(product.getStockQuantity()).isEqualTo(updateRequest.getStockQuantity());
        assertThat(product.getTopDescription()).isEqualTo(updateRequest.getTopDescription());
        assertThat(product.getBottomDescription()).isEqualTo(updateRequest.getBottomDescription());
    }

    @Test
    @DisplayName("상품 삭제")
    void 상품_삭제() {
        given(productRepository.findById(any())).willReturn(Optional.of(product));

        productService.deleteProduct(product.getId());

        given(productRepository.findById(product.getId())).willReturn(Optional.empty());

        assertThatThrownBy(() -> {
            productService.findById(product.getId());
        }).isInstanceOf(NoSuchElementException.class);
    }
}