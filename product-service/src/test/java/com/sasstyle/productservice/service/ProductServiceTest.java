package com.sasstyle.productservice.service;

import com.sasstyle.productservice.CategoryDummy;
import com.sasstyle.productservice.ProductDummy;
import com.sasstyle.productservice.client.UserServiceClient;
import com.sasstyle.productservice.controller.dto.ProductRequest;
import com.sasstyle.productservice.controller.dto.ProductUpdateRequest;
import com.sasstyle.productservice.client.dto.UserResponse;
import com.sasstyle.productservice.entity.Category;
import com.sasstyle.productservice.entity.Product;
import com.sasstyle.productservice.entity.ProductProfile;
import com.sasstyle.productservice.repository.CategoryRepository;
import com.sasstyle.productservice.repository.ProductRepository;
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

import static com.sasstyle.productservice.ProductDummy.BRAND_NAME;
import static com.sasstyle.productservice.ProductDummy.USER_ID;
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

    @Mock
    private UserServiceClient userServiceClient;

    private Category 의류;
    private Product product;
    private ProductProfile profile;
    private ProductRequest request;
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

        의류 = CategoryDummy.dummy(1L, null, "의류");
        profile = ProductProfile.builder()
                .profileUrl("https://picsum.photos/seed/picsum/200/300")
                .build();
        product = ProductDummy.dummy(1L,
                의류,
                profile,
                "한정판 맨투맨",
                10000,
                10,
                "상단 설명!",
                "하단 설명");
    }

    @Test
    @DisplayName("상품 등록")
    void 상품_등록() {
        given(categoryRepository.findById(의류.getId())).willReturn(Optional.of(의류));
        given(userServiceClient.findByUserId(USER_ID)).willReturn(new UserResponse(BRAND_NAME));
        given(productRepository.save(any())).willReturn(product);

        Long productId = productService.createProduct(USER_ID, request);

        assertThat(productId).isEqualTo(product.getId());
    }

    @Test
    @DisplayName("상품 수정")
    void 상품_수정() {
        given(productRepository.findProduct(any())).willReturn(product);

        ProductUpdateRequest updateRequest = new ProductUpdateRequest(
                "https://picsum.photos/seed/picsum/200/300",
                "예쁜 후드티",
                20000,
                100,
                "업데이트 상단 설명",
                "업데이트 하단 설명");

        productService.updateProduct(USER_ID, product.getId(), updateRequest);

        assertThat(product.getProductProfile().getProfileUrl()).isEqualTo(updateRequest.getProfileUrl());
        assertThat(product.getName()).isEqualTo(updateRequest.getName());
        assertThat(product.getPrice()).isEqualTo(updateRequest.getPrice());
        assertThat(product.getStockQuantity()).isEqualTo(updateRequest.getStockQuantity());
    }

    @Test
    @DisplayName("상품 삭제")
    void 상품_삭제() {
        given(productRepository.findById(any())).willReturn(Optional.of(product));

        productService.deleteProduct(USER_ID, product.getId());

        given(productRepository.findById(product.getId())).willReturn(Optional.empty());

        assertThatThrownBy(() -> {
            productService.findById(product.getId());
        }).isInstanceOf(NoSuchElementException.class);
    }
}