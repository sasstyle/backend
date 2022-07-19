package com.sasstyle.cartservice.service;

import com.sasstyle.cartservice.client.ProductServiceClient;
import com.sasstyle.cartservice.client.dto.ProductResponse;
import com.sasstyle.cartservice.controller.dto.CartDetailResponse;
import com.sasstyle.cartservice.controller.dto.CartResponse;
import com.sasstyle.cartservice.entity.Cart;
import com.sasstyle.cartservice.entity.CartDetail;
import com.sasstyle.cartservice.repository.CartDetailRepository;
import com.sasstyle.cartservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductServiceClient productServiceClient;

    public CartResponse findCart(String userId) {
        Cart cart = cartRepository.findCart(userId)
                .orElse(Cart.builder().id(null).build());

        List<CartDetailResponse> responses = new ArrayList<>();
        int price = 0;

        // 부하 테스트 필요
        for (CartDetail cartDetail : cart.getCartDetails()) {
            ProductResponse product = productServiceClient.findById(cartDetail.getProductId());
            responses.add(new CartDetailResponse(cartDetail.getId(),
                    product.getProfileUrl(),
                    product.getName(),
                    product.getPrice(),
                    cartDetail.getCount()));
            price += product.getPrice() * cartDetail.getCount();
        }

        return new CartResponse(cart.getId(), responses, price);
    }

    @Transactional
    public Long addCart(String userId, Long productId, int count) {
        Cart cart = cartRepository.findCart(userId)
                .orElse(Cart.builder().userId(userId).build());

        // 이미 상품을 추가한 경우
        if (hasProduct(getProductMap(cart.getCartDetails()), productId)) {
            List<CartDetail> cartDetails = getProductMap(cart.getCartDetails()).get(productId);
            CartDetail cartDetail = cartDetails.get(0);
            cartDetail.setCount(cartDetail.getCount() + count);
        } else {
            CartDetail cartDetail = CartDetail.builder()
                    .cart(cart)
                    .productId(productId)
                    .count(count)
                    .build();

            cart.addCartDetail(cartDetail);
        }

        return cartRepository.save(cart).getId();
    }

    @Transactional
    public void updateCount(String userId, Long cartDetailId, int count) {
        CartDetail detail = cartDetailRepository.findByUserIdAndId(userId, cartDetailId);
        if (detail == null) {
            throw new IllegalArgumentException("장바구니 상품 수량을 업데이트 할 수 없습니다.");
        }

        detail.setCount(count);
    }

    @Transactional
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Transactional
    public void deleteCartDetail(Long cartDetailId) {
        cartDetailRepository.deleteById(cartDetailId);
    }

    private boolean hasProduct(Map<Long, List<CartDetail>> productMap, Long productId) {
        return productMap.containsKey(productId);
    }

    private Map<Long, List<CartDetail>> getProductMap(List<CartDetail> cartDetails) {
        return cartDetails.stream()
                .collect(groupingBy(CartDetail::getProductId));
    }

}
