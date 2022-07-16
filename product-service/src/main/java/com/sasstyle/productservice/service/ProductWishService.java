package com.sasstyle.productservice.service;

import com.sasstyle.productservice.controller.dto.WishResponse;
import com.sasstyle.productservice.entity.Product;
import com.sasstyle.productservice.entity.ProductWish;
import com.sasstyle.productservice.repository.ProductWishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductWishService {

    private final ProductService productService;
    private final ProductWishRepository productWishRepository;

    public Page<WishResponse> findWishList(String userId, Pageable pageable) {
        return productWishRepository.findAllWithProduct(userId, pageable);
    }

    @Transactional
    public Long wish(String userId, Long productId) {
        Product product = productService.findProduct(productId);

        if (productWishRepository.existsWish(userId, productId)) {
            throw new IllegalArgumentException("이미 위시리스트에 추가했습니다.");
        }

        ProductWish wish = ProductWish.builder()
                .userId(userId)
                .product(product)
                .build();

        return productWishRepository.save(wish).getId();
    }

    @Transactional
    public void unWish(String userId, Long productId) {
        productWishRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
