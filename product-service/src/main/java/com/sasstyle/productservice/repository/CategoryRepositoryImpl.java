package com.sasstyle.productservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sasstyle.productservice.entity.Category;
import com.sasstyle.productservice.entity.QCategory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sasstyle.productservice.entity.QCategory.category;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Category> findAllWithChildren() {
        QCategory children = new QCategory("children");

        return queryFactory
                .selectFrom(category)
                .join(category.children, children).fetchJoin()
                .where(category.parent.isNull())
                .fetch();
    }
}
