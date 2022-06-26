package com.sasstyle.productservice.repository;

import com.sasstyle.productservice.controller.dto.ProductResponse;
import com.sasstyle.productservice.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductQueryRepository {

    Product findProduct(Long id);

    Map<Long, List<ProductResponse>> findProductMap(List<Long> categoryIds, Pageable pageable);
}
