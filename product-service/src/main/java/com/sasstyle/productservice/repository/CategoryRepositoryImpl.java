package com.sasstyle.productservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sasstyle.productservice.controller.dto.CategoryResponse;
import com.sasstyle.productservice.entity.Category;
import com.sasstyle.productservice.entity.QCategory;
import com.sasstyle.productservice.entity.QProduct;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.sasstyle.productservice.entity.QCategory.category;
import static com.sasstyle.productservice.entity.QProduct.product;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CategoryResponse> findAllWithChildren() {
        QCategory children = new QCategory("children");

        List<Category> result = queryFactory
                .selectFrom(category)
                .leftJoin(category.children, children).fetchJoin()
                .where(category.parent.isNull())
                .fetch();


        return result.stream().map(CategoryResponse::new).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse findByIdWithChildren(Long id) {
        QCategory children = new QCategory("children");

        Category result = queryFactory
                .selectFrom(category)
                .leftJoin(category.children, children).fetchJoin()
                .where(category.id.eq(id))
                .fetchOne();

        return new CategoryResponse(result);
    }

    @Override
    public Category findByIdWithProduct(Long id) {
        return queryFactory
                .selectFrom(category)
                .leftJoin(category.products, product).fetchJoin()
                .where(category.id.eq(id))
                .fetchOne();
    }
}
