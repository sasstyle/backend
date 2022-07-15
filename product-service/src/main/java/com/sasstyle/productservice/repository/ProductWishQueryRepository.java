package com.sasstyle.productservice.repository;

public interface ProductWishQueryRepository {

    boolean existsWish(String userId, Long productId);
}
