package com.sasstyle.productservice.service;

import com.sasstyle.productservice.CategoryDummy;
import com.sasstyle.productservice.ProductDummy;
import com.sasstyle.productservice.client.UserServiceClient;
import com.sasstyle.productservice.client.dto.UserResponse;
import com.sasstyle.productservice.controller.dto.ProductRequest;
import com.sasstyle.productservice.controller.dto.ProductUpdateRequest;
import com.sasstyle.productservice.entity.Category;
import com.sasstyle.productservice.entity.Product;
import com.sasstyle.productservice.repository.CategoryRepository;
import com.sasstyle.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.sasstyle.productservice.UserDummy.BRAND_NAME;
import static com.sasstyle.productservice.UserDummy.USER_ID;
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
    private ProductRequest 상품_요청;
    private Product 더미_상품;

    @BeforeEach
    void setUp() {
        상품_요청 = ProductDummy.상품_요청();
        의류 = CategoryDummy.의류();
        더미_상품 = ProductDummy.한정판_맨투맨();
    }

    @Test
    @DisplayName("상품 등록")
    void 상품_등록() {
        given(categoryRepository.findById(의류.getId())).willReturn(Optional.of(의류));
        given(userServiceClient.findByUserId(USER_ID)).willReturn(new UserResponse(BRAND_NAME));
        given(productRepository.save(any())).willReturn(더미_상품);

        Long productId = productService.createProduct(USER_ID, 상품_요청);

        assertThat(productId).isEqualTo(더미_상품.getId());
    }

    @Test
    @DisplayName("상품 수정")
    void 상품_수정() {
        given(productRepository.findProduct(userId, any())).willReturn(더미_상품);

        ProductUpdateRequest updateRequest = new ProductUpdateRequest(
                "https://picsum.photos/seed/picsum/200/300",
                "예쁜 후드티",
                20000,
                100);

        productService.updateProduct(USER_ID, 더미_상품.getId(), updateRequest);

        assertThat(더미_상품.getProductProfile().getProfileUrl()).isEqualTo(updateRequest.getProfileUrl());
        assertThat(더미_상품.getName()).isEqualTo(updateRequest.getName());
        assertThat(더미_상품.getPrice()).isEqualTo(updateRequest.getPrice());
        assertThat(더미_상품.getStockQuantity()).isEqualTo(updateRequest.getStockQuantity());
    }

    @Test
    @DisplayName("상품 삭제")
    void 상품_삭제() {
        given(productRepository.findById(any())).willReturn(Optional.of(더미_상품));

        productService.deleteProduct(USER_ID, 더미_상품.getId());

        given(productRepository.findById(더미_상품.getId())).willReturn(Optional.empty());

        assertThatThrownBy(() -> {
            productService.findById(더미_상품.getId());
        }).isInstanceOf(NoSuchElementException.class);
    }
}