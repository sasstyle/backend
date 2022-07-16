package com.sasstyle.productservice.repository;

import com.sasstyle.productservice.controller.dto.ProductDetailResponse;
import com.sasstyle.productservice.controller.dto.ProductSimpleResponse;
import com.sasstyle.productservice.controller.dto.ProductResponse;
import com.sasstyle.productservice.controller.dto.ProductSearch;
import com.sasstyle.productservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductQueryRepository {

    Product findProduct(Long id);

    ProductDetailResponse findProductWithWish(String userId, Long productId);

    Page<ProductResponse> findProducts(String userId, Pageable pageable);

    Page<ProductResponse> findAllByCategoryIds(String userId, List<Long> categoryIds, Pageable pageable);

    Page<ProductResponse> search(String userId, ProductSearch productSearch, Pageable pageable);

    List<ProductSimpleResponse> findAllSimple(ProductSearch productSearch, Pageable pageable);
}
