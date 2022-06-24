package com.sasstyle.productservice.repository;

import com.sasstyle.productservice.entity.Category;

import java.util.List;

public interface CategoryQueryRepository {

    List<Category> findAllWithChildren();

    Category findByIdWithChildren(Long id);
}
