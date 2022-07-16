package com.sasstyle.productservice.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sasstyle.productservice.controller.dto.*;
import com.sasstyle.productservice.entity.Product;
import com.sasstyle.productservice.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

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
    public Page<ProductResponse> findProducts(Pageable pageable) {
        List<ProductResponse> content = queryFactory
                .select(new QProductResponse(
                        product.category.id,
                        product.id,
                        product.productProfile.profileUrl,
                        product.name,
                        product.brandName,
                        product.price))
                .from(product)
                .distinct()
                .join(product.category, category)
                .join(product.productProfile, productProfile)
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
    public Page<ProductResponse> searchInQuery(List<Long> categoryIds, Pageable pageable) {
        List<ProductResponse> content = queryFactory
                .select(new QProductResponse(
                        product.category.id,
                        product.id,
                        product.productProfile.profileUrl,
                        product.name,
                        product.brandName,
                        product.price))
                .from(product)
                .distinct()
                .join(product.category, category)
                .join(product.productProfile, productProfile)
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

    @Override
    public Page<ProductResponse> search(ProductSearch productSearch, Pageable pageable) {
        List<ProductResponse> content = queryFactory
                .select(new QProductResponse(
                        product.category.id,
                        product.id,
                        product.productProfile.profileUrl,
                        product.name,
                        product.brandName,
                        product.price))
                .from(product)
                .join(product.category, category)
                .join(product.productProfile, productProfile)
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
    public List<ProductAutoCompleteResponse> autocomplete(ProductSearch productSearch, Pageable pageable) {
        return queryFactory
                .select(new QProductAutoCompleteResponse(product.id, product.productProfile.profileUrl, product.name, product.brandName))
                .from(product)
                .join(product.productProfile, productProfile)
                .where(nameContains(productSearch.getName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderById(productSearch.getSort()))
                .fetch();
    }
    
    private BooleanExpression productIdEq(Long id) {
        return product.id.eq(id);
    }

    private BooleanExpression nameContains(String name) {
        return product.name.contains(name);
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
