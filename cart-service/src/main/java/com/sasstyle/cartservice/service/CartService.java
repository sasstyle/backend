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
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductServiceClient productServiceClient;

    public Cart findByUserId(String userId) {
        return cartRepository.findCart(userId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니를 찾을 수 없습니다."));
    }

    public CartResponse findCart(String userId) {
        Cart cart = findByUserId(userId);

        List<CartDetailResponse> responses = new ArrayList<>();
        int price = 0;

        // 부하 테스트 필요
        for (CartDetail cartDetail : cart.getCartDetails()) {
            ProductResponse product = productServiceClient.findById(cartDetail.getProductId());
            responses.add(new CartDetailResponse(
                    cartDetail.getId(),
                    cartDetail.getProductId(),
                    product.getProfileUrl(),
                    product.getName(),
                    product.getBrandName(),
                    product.getPrice(),
                    cartDetail.getCount()));
            price += product.getPrice() * cartDetail.getCount();
        }

        return new CartResponse(cart.getId(), responses, price);
    }

    @Transactional
    public void createCart(String userId) {
        Cart cart = Cart.builder()
                .userId(userId)
                .build();

        cartRepository.save(cart);
    }

    @Transactional
    public void addCart(String userId, Long productId, int count) {
        Cart cart = findByUserId(userId);

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
    public void deleteCart(String userId) {
        Cart cart = findByUserId(userId);

        if (cart.getCartDetails() != null && !cart.getCartDetails().isEmpty()) {
            List<Long> ids = cart.getCartDetails().stream()
                    .map(CartDetail::getId)
                    .collect(toList());

            cartDetailRepository.deleteAllByIdInQuery(ids);
        }
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
