package com.sasstyle.reviewservice.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sasstyle.reviewservice.controller.dto.ReviewResponse;
import com.sasstyle.reviewservice.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sasstyle.reviewservice.entity.QReview.review;
import static com.sasstyle.reviewservice.entity.QReviewImage.reviewImage;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReviewResponse> findAllByProductId(Long productId, Pageable pageable) {
        List<Review> result = queryFactory
                .selectFrom(review).distinct()
                .leftJoin(review.reviewImages, reviewImage).fetchJoin()
                .where(productIdEq(productId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(review.createdAt.desc())
                .fetch();

        List<ReviewResponse> content = result.stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());

        JPAQuery<Long> countQuery = queryFactory
                .select(review.count()).from(review).distinct()
                .leftJoin(review.reviewImages, reviewImage).fetchJoin()
                .where(productIdEq(productId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Optional<Review> findByIdWithReviewImage(Long reviewId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(review).distinct()
                        .leftJoin(review.reviewImages, reviewImage).fetchJoin()
                        .where(review.id.eq(reviewId))
                        .fetchOne()
        );
    }

    private BooleanExpression productIdEq(Long productId) {
        return productId == null ? null : review.productId.eq(productId);
    }
}
