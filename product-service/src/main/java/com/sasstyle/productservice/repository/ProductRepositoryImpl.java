package com.sasstyle.productservice.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sasstyle.productservice.controller.dto.*;
import com.sasstyle.productservice.entity.Product;
import com.sasstyle.productservice.entity.QProductWish;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sasstyle.productservice.entity.QCategory.category;
import static com.sasstyle.productservice.entity.QProduct.product;
import static com.sasstyle.productservice.entity.QProductImage.productImage;
import static com.sasstyle.productservice.entity.QProductProfile.productProfile;
import static com.sasstyle.productservice.entity.QProductWish.productWish;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductQueryRepository {

    private static final String ID_ASC = "idAsc";
    private static final String ID_DESC = "idDesc";

    private final JPAQueryFactory queryFactory;

    @Override
    public Product findProduct(Long id) {
        return queryFactory
                .selectFrom(product).distinct()
                .join(product.category, category).fetchJoin()
                .join(product.productProfile, productProfile).fetchJoin()
                .leftJoin(product.productImages, productImage).fetchJoin()
                .where(productIdEq(id))
                .fetchOne();
    }

    @Override
    public ProductDetailResponse findProductWithWish(String userId, Long productId) {
        Product result = queryFactory
                .selectFrom(product).distinct()
                .join(product.category, category).fetchJoin()
                .join(product.productProfile, productProfile).fetchJoin()
                .leftJoin(product.productImages, productImage).fetchJoin()
                .leftJoin(product.productWishes, productWish).on(userIdEq(userId))
                .where(productIdEq(productId))
                .fetchOne();

        return new ProductDetailResponse(result.getProductProfile().getProfileUrl(),
                result.getName(),
                result.getBrandName(),
                result.getPrice(),
                result.getProductImages(),
                result.getProductWishes().isEmpty() ? false : true);
    }

    @Override
    public Page<ProductResponse> findProducts(String userId, Pageable pageable) {
        List<ProductResponse> content = queryFactory
                .select(new QProductResponse(
                        product.category.id,
                        product.id,
                        product.productProfile.profileUrl,
                        product.name,
                        product.brandName,
                        product.price,
                        new CaseBuilder()
                                .when(productWish.id.isNotNull()).then(true)
                                .otherwise(false)
                        )
                )
                .from(product)
                .distinct()
                .join(product.category, category)
                .join(product.productProfile, productProfile)
                .leftJoin(product.productWishes, productWish).on(userIdEq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(product.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(product.count()).from(product).distinct()
                .join(product.category, category)
                .join(product.productProfile, productProfile);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<ProductResponse> search(String userId, ProductSearch productSearch, Pageable pageable) {
        List<ProductResponse> content = queryFactory
                .select(new QProductResponse(
                        product.category.id,
                        product.id,
                        product.productProfile.profileUrl,
                        product.name,
                        product.brandName,
                        product.price,
                        new CaseBuilder()
                                .when(productWish.id.isNotNull()).then(true)
                                .otherwise(false)
                        )
                )
                .from(product)
                .join(product.category, category)
                .join(product.productProfile, productProfile)
                .leftJoin(product.productWishes, productWish).on(userIdEq(userId))
                .where(nameContains(productSearch.getName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderById(productSearch.getSort()))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(product.count()).from(product)
                .join(product.category, category)
                .join(product.productProfile, productProfile)
                .where(nameContains(productSearch.getName()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<ProductSimpleResponse> findAllSimple(ProductSearch productSearch, Pageable pageable) {
        return queryFactory
                .select(new QProductSimpleResponse(product.id, product.productProfile.profileUrl, product.name, product.brandName))
                .from(product)
                .join(product.productProfile, productProfile)
                .where(nameContains(productSearch.getName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderById(productSearch.getSort()))
                .fetch();
    }

    @Override
    public Page<ProductResponse> findAllByCategoryIds(String userId, List<Long> categoryIds, Pageable pageable) {
        List<ProductResponse> content = queryFactory
                .select(new QProductResponse(
                        product.category.id,
                        product.id,
                        product.productProfile.profileUrl,
                        product.name,
                        product.brandName,
                        product.price,
                        new CaseBuilder()
                                .when(productWish.id.isNotNull()).then(true)
                                .otherwise(false)
                        )
                )
                .from(product)
                .distinct()
                .join(product.category, category)
                .join(product.productProfile, productProfile)
                .leftJoin(product.productWishes, productWish).on(userIdEq(userId))
                .where(category.id.in(categoryIds))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(product.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(product.count()).from(product).distinct()
                .join(product.category, category)
                .join(product.productProfile, productProfile)
                .where(category.id.in(categoryIds));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression productIdEq(Long id) {
        return product.id.eq(id);
    }

    private BooleanExpression nameContains(String name) {
        return product.name.contains(name);
    }

    private BooleanExpression userIdEq(String userId) {
        if (!StringUtils.hasText(userId)) {
            return productWish.userId.isNull();
        }

        return productWish.userId.eq(userId);
    }

    private OrderSpecifier<Long> orderById(String sort) {
        if (!hasText(sort)) {
            return product.id.desc();
        }

        switch (sort) {
            case ID_ASC:
                return product.id.asc();
            case ID_DESC:
            default:
                return product.id.desc();
        }
    }
}
