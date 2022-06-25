package com.sasstyle.productservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sasstyle.productservice.controller.dto.CategoryResponse;
import com.sasstyle.productservice.entity.Category;
import com.sasstyle.productservice.entity.QCategory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.sasstyle.productservice.entity.QCategory.category;

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
                .selectFrom(QCategory.category)
                .leftJoin(QCategory.category.children, children).fetchJoin()
                .where(QCategory.category.id.eq(id))
                .fetchOne();

        return new CategoryResponse(result);
    }
}
