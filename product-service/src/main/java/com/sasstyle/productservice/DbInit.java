package com.sasstyle.productservice;

import com.sasstyle.productservice.controller.dto.ProductRequest;
import com.sasstyle.productservice.entity.Category;
import com.sasstyle.productservice.repository.CategoryRepository;
import com.sasstyle.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
//@Component
public class DbInit {

    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    @PostConstruct
    public void init() {
        // 카테고리 샘플 데이터
        Category top = Category.create(null, "상의");
        Category tshirt = Category.create(top, "티셔츠");
        Category blouse = Category.create(top, "블라우스");
        Category sweaTshirt = Category.create(top, "맨투맨");

        top.addChildren(tshirt);
        top.addChildren(blouse);
        top.addChildren(sweaTshirt);

        categoryRepository.save(top);

        // 상품 샘플 데이터
        for (int i = 1; i <= 100; i++) {
            ProductRequest productRequest = new ProductRequest(
                    top.getId(),
                    "https://picsum.photos/seed/picsum/200/300",
                    "괜찮은 맨투맨[" + i + "]", 10000,
                    10,
                    "상단 설명",
                    "하단 설명",
                    List.of("https://picsum.photos/seed/picsum/200/300", "https://picsum.photos/seed/picsum/400/600")
            );

            productService.createProduct("823bb5ef-c61d-4889-93ee-0aaf2caa65d4", productRequest);
        }
    }
}
