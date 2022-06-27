package com.sasstyle.productservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sasstyle.productservice.controller.dto.CategoryResponse;
import com.sasstyle.productservice.entity.Category;
import com.sasstyle.productservice.entity.QCategory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.sasstyle.productservice.entity.QCategory.category;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

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

        return result.stream().map(CategoryResponse::new).collect(toList());
    }

    @Override
    public List<Long> findCategoryIds(Long id) {
        QCategory children = new QCategory("children");

        Category result = queryFactory
                .selectFrom(category)
                .leftJoin(category.children, children).fetchJoin()
                .where(category.id.eq(id))
                .fetchOne();

        return toCategoryIds(result);
    }

    private List<Long> toCategoryIds(Category category) {
        List<Long> result = new ArrayList<>();

        if (isNull(category)) {
            return result;
        }

        result.add(category.getId());

        for (Category c : category.getChildren()) {
            result.addAll(toCategoryIds(c));
        }

        return result;
    }
}
