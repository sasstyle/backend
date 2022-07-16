package com.sasstyle.productservice.repository;

import com.sasstyle.productservice.controller.dto.CategoryResponse;

import java.util.List;

public interface CategoryQueryRepository {

    List<CategoryResponse> findAllWithChildren();

    List<Long> findWithChildrenIds(Long id);
}
