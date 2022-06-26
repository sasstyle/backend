package com.sasstyle.productservice.service;

import com.sasstyle.productservice.controller.dto.ProductRequest;
import com.sasstyle.productservice.controller.dto.ProductUpdateRequest;
import com.sasstyle.productservice.entity.Category;
import com.sasstyle.productservice.entity.Product;
import com.sasstyle.productservice.entity.ProductDetail;
import com.sasstyle.productservice.repository.CategoryRepository;
import com.sasstyle.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("상품을 찾을 수 없습니다."));
    }

    @Transactional
    public Long createProduct(ProductRequest request) {
        // 카테고리 조회
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalStateException("카테고리를 찾을 수 없습니다."));

        // 상품 생성
        Product product = Product.create(category, request);

        // 상품 디테일 이미지 생성
        request.getDetailImages().stream()
                .map(ProductDetail::new)
                .forEach(product::addDetailImage);

        return productRepository.save(product).getId();
    }

    @Transactional
    public void updateProduct(Long productId, ProductUpdateRequest request) {
        Product product = findById(productId);

        product.update(request);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = findById(productId);

        productRepository.delete(product);
    }
}
