package com.sasstyle.productservice.service;

import com.sasstyle.productservice.client.UserServiceClient;
import com.sasstyle.productservice.controller.dto.*;
import com.sasstyle.productservice.entity.Category;
import com.sasstyle.productservice.entity.Product;
import com.sasstyle.productservice.entity.ProductImage;
import com.sasstyle.productservice.entity.ProductProfile;
import com.sasstyle.productservice.repository.CategoryRepository;
import com.sasstyle.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserServiceClient userServiceClient;

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

    public Page<ProductResponse> findProducts(Pageable pageable) {
        return productRepository.findProducts(pageable);
    }

    public Page<ProductResponse> search(ProductSearch productSearch, Pageable pageable) {
        return productRepository.search(productSearch, pageable);
    }

    public Page<ProductResponse> searchInQuery(List<Long> categoryIds, Pageable pageable) {
        return productRepository.searchInQuery(categoryIds, pageable);
    }

    public List<ProductAutoCompleteResponse> autocomplete(ProductSearch productSearch, Pageable pageable) {
        return productRepository.autocomplete(productSearch, pageable);
    }

    @Transactional
    public Long createProduct(String userId, ProductRequest request) {
        // 카테고리 조회
        Category findCategory = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("카테고리를 찾을 수 없습니다."));

        // 사용자 조회
        UserResponse userResponse = userServiceClient.findByUserId(userId);

        // 상품 프로필 생성
        ProductProfile productProfile = ProductProfile.builder()
                .profileUrl(request.getProfileUrl())
                .build();

        // 상품 상세 이미지 생성
        List<ProductImage> productImages = new ArrayList<>();
        if (isUploadImages(request.getImages())) {
            productImages = request.getImages().stream()
                    .map(ProductImage::new)
                    .collect(Collectors.toList());
        }

        // 상품 생성
        Product product = Product.builder()
                .category(findCategory)
                .productProfile(productProfile)
                .userId(userId)
                .brandName(userResponse.getName())
                .name(request.getName())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .topDescription(request.getTopDescription())
                .bottomDescription(request.getBottomDescription())
                .productImages(productImages)
                .build();

        return productRepository.save(product).getId();
    }

    @Transactional
    public void updateProduct(String userId, Long productId, ProductUpdateRequest request) {
        Product product = findProduct(productId);

        if (isNotValidSeller(userId, product.getUserId())) {
            throw new IllegalArgumentException("상품 판매자와 로그인한 사용자가 일치하지 않습니다.");
        }

        // 상품 프로필 업데이트
        if (hasProfileUrl(request.getProfileUrl())) {
            ProductProfile productProfile = product.getProductProfile();
            productProfile.setProfileUrl(request.getProfileUrl());
        }

        product.update(request.getName(),
                request.getPrice(),
                request.getStockQuantity(),
                request.getTopDescription(),
                request.getBottomDescription());
    }

    @Transactional
    public void deleteProduct(String userId, Long productId) {
        Product product = findById(productId);

        if (isNotValidSeller(userId, product.getUserId())) {
            throw new IllegalArgumentException("상품 판매자와 로그인한 사용자가 일치하지 않습니다.");
        }

        productRepository.delete(product);
    }

    private boolean isNotValidSeller(String userId, String productUserId) {
        return !userId.equals(productUserId);
    }

    private boolean hasProfileUrl(String profileUrl) {
        return StringUtils.hasText(profileUrl);
    }

    private boolean isUploadImages(List<String> images) {
        return images != null && !images.isEmpty();
    }
}
