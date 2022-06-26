package com.sasstyle.productservice.service;

import com.sasstyle.productservice.controller.dto.ProductRequest;
import com.sasstyle.productservice.controller.dto.ProductResponse;
import com.sasstyle.productservice.controller.dto.ProductUpdateRequest;
import com.sasstyle.productservice.entity.Category;
import com.sasstyle.productservice.entity.Product;
import com.sasstyle.productservice.entity.ProductDetail;
import com.sasstyle.productservice.repository.CategoryRepository;
import com.sasstyle.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다."));
    }

    public Product findProduct(Long productId) {
        Product product = productRepository.findProduct(productId);

        if (isNull(product)) {
            throw new NoSuchElementException("상품을 찾을 수 없습니다.");
        }

        return product;
    }

    public Map<Long, List<ProductResponse>> findProductMap(List<Long> categoryIds, Pageable pageable) {
        return productRepository.findProductMap(categoryIds, pageable);
    }

    @Transactional
    public Long createProduct(ProductRequest request) {
        // 카테고리 조회
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("카테고리를 찾을 수 없습니다."));

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
