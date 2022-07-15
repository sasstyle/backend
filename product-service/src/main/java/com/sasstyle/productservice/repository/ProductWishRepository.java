package com.sasstyle.productservice.repository;

import com.sasstyle.productservice.entity.ProductWish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductWishRepository extends JpaRepository<ProductWish, Long>, ProductWishQueryRepository {

    void deleteByUserIdAndProductId(String userId, Long productId);
}
