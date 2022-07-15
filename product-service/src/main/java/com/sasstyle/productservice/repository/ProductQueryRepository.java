package com.sasstyle.productservice.repository;

import com.sasstyle.productservice.controller.dto.ProductAutoCompleteResponse;
import com.sasstyle.productservice.controller.dto.ProductResponse;
import com.sasstyle.productservice.controller.dto.ProductSearch;
import com.sasstyle.productservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductQueryRepository {

    Product findProduct(Long id);

    Page<ProductResponse> findProducts(Pageable pageable);

    Page<ProductResponse> searchInQuery(List<Long> categoryIds, Pageable pageable);

    Page<ProductResponse> search(ProductSearch productSearch, Pageable pageable);

    List<ProductAutoCompleteResponse> autocomplete(ProductSearch productSearch, Pageable pageable);

    Page<ProductResponse> findProductsWithWish(String userId, Pageable pageable);
}
