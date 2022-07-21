package com.sasstyle.cartservice.service;

import com.sasstyle.cartservice.CartDetailDummy;
import com.sasstyle.cartservice.CartDummy;
import com.sasstyle.cartservice.client.ProductServiceClient;
import com.sasstyle.cartservice.client.dto.ProductResponse;
import com.sasstyle.cartservice.controller.dto.CartResponse;
import com.sasstyle.cartservice.entity.Cart;
import com.sasstyle.cartservice.entity.CartDetail;
import com.sasstyle.cartservice.repository.CartDetailRepository;
import com.sasstyle.cartservice.repository.CartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.sasstyle.cartservice.CartDetailDummy.*;
import static com.sasstyle.cartservice.CartDummy.CART_ID;
import static com.sasstyle.cartservice.ProductDummy.*;
import static com.sasstyle.cartservice.UserDummy.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartDetailRepository cartDetailRepository;

    @Mock
    private ProductServiceClient productServiceClient;

    @DisplayName("장바구니 상품 추가")
    @Test
    void 장바구니_상품_추가() {
        int count = 1;

        Cart cart = CartDummy.dummy();
        given(cartRepository.save(any())).willReturn(cart);

        cartService.addCart(USER_ID, PRODUCT_ID, count);
        given(cartRepository.findCart(USER_ID)).willReturn(Optional.of(cart));

        CartResponse response = cartService.findCart(USER_ID);

        assertThat(response.getCartId()).isEqualTo(CART_ID);
    }

    @DisplayName("장바구니 상품이 존재하는 경우 추가")
    @Test
    void 장바구니에_상품이_존재하는_경우_추가() {
        Cart cart = CartDummy.dummy();
        CartDetail cartDetail = CartDetailDummy.dummy();

        cart.addCartDetail(cartDetail);

        given(cartRepository.findCart(USER_ID)).willReturn(Optional.of(cart));
        cartService.addCart(USER_ID, PRODUCT_ID, ADD_COUNT);

        given(cartRepository.findCart(USER_ID)).willReturn(Optional.of(cart));
        given(productServiceClient.findById(PRODUCT_ID)).willReturn(new ProductResponse(PROFILE_URL, NAME, BRAND_NAME, PRICE));
        CartResponse response = cartService.findCart(USER_ID);

        assertThat(response.getCartId()).isEqualTo(CART_ID);
        assertThat(response.getProducts().size()).isEqualTo(1);
        assertThat(response.getTotalPrice()).isEqualTo(PRICE * (COUNT + ADD_COUNT));
    }

    @DisplayName("장바구니 수량 수정 - 상품이 존재하지 않을 경우")
    @Test
    void 장바구니_수량_수정_상품X() {
        given(cartDetailRepository.findByUserIdAndId(USER_ID, CART_DETAIL_ID)).willReturn(dummy());
        cartService.updateCount(USER_ID, CART_DETAIL_ID, UPDATE_COUNT);

        given(cartDetailRepository.findByUserIdAndId(USER_ID, CART_DETAIL_ID)).willReturn(null);
        assertThatThrownBy(() -> cartService.updateCount(USER_ID, CART_DETAIL_ID, UPDATE_COUNT))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("장바구니 수량 수정")
    @Test
    void 장바구니_수량_수정() {
        given(cartDetailRepository.findByUserIdAndId(USER_ID, CART_DETAIL_ID)).willReturn(dummy());
        cartService.updateCount(USER_ID, CART_DETAIL_ID, UPDATE_COUNT);

        given(cartDetailRepository.findById(CART_DETAIL_ID)).willReturn(Optional.of(updateDummy()));
        CartDetail detail = cartDetailRepository.findById(CART_DETAIL_ID).get();

        assertThat(detail.getId()).isEqualTo(CART_DETAIL_ID);
        assertThat(detail.getCount()).isEqualTo(UPDATE_COUNT);
    }

    @DisplayName("장바구니 전체 삭제")
    @Test
    void 장바구니_삭제() {
        cartService.deleteCart(CART_ID);

        given(cartRepository.findById(CART_ID)).willReturn(Optional.empty());
        assertThat(cartRepository.findById(CART_ID).isEmpty()).isTrue();
    }

    @DisplayName("장바구니 상품 삭제")
    @Test
    void 장바구니_상품_삭제() {
         cartService.deleteCartDetail(CART_DETAIL_ID);

        given(cartDetailRepository.findById(CART_DETAIL_ID)).willReturn(Optional.empty());
        assertThat(cartDetailRepository.findById(CART_DETAIL_ID).isEmpty()).isTrue();
    }
}