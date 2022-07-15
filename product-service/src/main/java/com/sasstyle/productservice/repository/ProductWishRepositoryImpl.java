package com.sasstyle.productservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.sasstyle.productservice.entity.QProductWish.productWish;

@RequiredArgsConstructor
public class ProductWishRepositoryImpl implements ProductWishQueryRepository {

    private final JPAQueryFactory queryFactory;

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
