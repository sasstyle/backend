package com.sasstyle.productservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sasstyle.productservice.controller.dto.ProductResponse;
import com.sasstyle.productservice.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sasstyle.productservice.entity.QCategory.category;
import static com.sasstyle.productservice.entity.QProduct.product;
import static com.sasstyle.productservice.entity.QProductDetail.productDetail;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Product findProduct(Long id) {
        return queryFactory
                .selectFrom(product)
                .leftJoin(product.productDetails, productDetail).fetchJoin()
                .where(product.id.eq(id))
                .fetchOne();
    }

    @Override
    public Map<Long, List<ProductResponse>> findProductMap(List<Long> categoryIds, Pageable pageable) {
        List<Product> products = queryFactory
                .selectFrom(product).distinct()
                .join(product.category, category).fetchJoin()
                .where(category.id.in(categoryIds))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(product.createdAt.desc())
                .fetch();

        return products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.groupingBy((p) -> p.getCategoryId()));
    }
}
