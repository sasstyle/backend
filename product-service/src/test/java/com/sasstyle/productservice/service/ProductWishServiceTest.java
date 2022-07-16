package com.sasstyle.productservice.service;

import com.sasstyle.productservice.ProductDummy;
import com.sasstyle.productservice.WishDummy;
import com.sasstyle.productservice.controller.dto.WishResponse;
import com.sasstyle.productservice.repository.ProductRepository;
import com.sasstyle.productservice.repository.ProductWishRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;

import static com.sasstyle.productservice.UserDummy.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductWishServiceTest {

    @InjectMocks
    private ProductWishService productWishService;

    @Mock
    private ProductService productService;

    @Mock
    private ProductWishRepository productWishRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    void 위시리스트_추가() {
        Long productId = 1L;

        given(productService.findProduct(any())).willReturn(ProductDummy.한정판_맨투맨());
        given(productWishRepository.existsWish(USER_ID, productId)).willReturn(false);
        given(productWishRepository.save(any())).willReturn(WishDummy.더미());

        Long savedId = productWishService.wish(USER_ID, productId);
        assertThat(savedId).isEqualTo(1L);
    }

    @Test
    void 위시리스트_삭제() {
        Long productId = 1L;

        productWishService.unWish(USER_ID, productId);

        given(productWishRepository.findAllWithProduct(any(), any())).willReturn(new PageImpl<>(new ArrayList<>()));
        Page<WishResponse> wishList = productWishService.findWishList(USER_ID, null);

        assertThat(wishList.getTotalElements()).isEqualTo(0);
        assertThat(wishList.getSize()).isEqualTo(0);
    }
}