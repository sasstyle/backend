package com.sasstyle.productservice.repository;

import com.sasstyle.productservice.controller.dto.ProductSimpleResponse;
import com.sasstyle.productservice.controller.dto.ProductResponse;
import com.sasstyle.productservice.controller.dto.ProductSearch;
import com.sasstyle.productservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductQueryRepository {

    Product findProduct(Long id);

    Page<ProductResponse> findProducts(Pageable pageable);

    Page<ProductResponse> findAllByCategoryIds(List<Long> categoryIds, Pageable pageable);

    Page<ProductResponse> search(ProductSearch productSearch, Pageable pageable);

    List<ProductSimpleResponse> findAllSimple(ProductSearch productSearch, Pageable pageable);
}
