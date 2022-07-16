package com.sasstyle.productservice.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sasstyle.productservice.controller.dto.QWishResponse;
import com.sasstyle.productservice.controller.dto.WishResponse;
import com.sasstyle.productservice.entity.ProductWish;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.sasstyle.productservice.entity.QCategory.category;
import static com.sasstyle.productservice.entity.QProduct.product;
import static com.sasstyle.productservice.entity.QProductProfile.productProfile;
import static com.sasstyle.productservice.entity.QProductWish.productWish;

@RequiredArgsConstructor
public class ProductWishRepositoryImpl implements ProductWishQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<WishResponse> findAllWithProduct(String userId, Pageable pageable) {
        List<WishResponse> content = queryFactory
                .select(new QWishResponse(product.id,
                        productProfile.profileUrl,
                        product.name,
                        product.brandName,
                        product.price))
                .from(productWish)
                .join(productWish.product, product)
                .join(product.productProfile, productProfile)
                .where(productWish.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(productWish.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(productWish.count()).from(productWish)
                .join(productWish.product, product)
                .join(product.productProfile, productProfile)
                .where(productWish.userId.eq(userId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public boolean existsWish(String userId, Long productId) {
        Integer count = queryFactory
                .selectOne()
                .from(productWish)
                .where(productWish.userId.eq(userId).and(productWish.product.id.eq(productId)))
                .fetchFirst();

        return count != null;
    }
}
