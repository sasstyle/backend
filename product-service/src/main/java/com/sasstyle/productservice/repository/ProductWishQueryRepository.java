package com.sasstyle.productservice.repository;

import com.sasstyle.productservice.controller.dto.WishResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductWishQueryRepository {

    Page<WishResponse> findAllWithProduct(String userId, Pageable pageable);

    boolean existsWish(String userId, Long productId);
}
