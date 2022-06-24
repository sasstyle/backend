package com.sasstyle.productservice.service;

import com.sasstyle.productservice.controller.dto.CategoryResponse;
import com.sasstyle.productservice.entity.Category;
import com.sasstyle.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public List<CategoryResponse> findAllWithChildren() {
        return categoryRepository.findAllWithChildren().stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());

    }

    public CategoryResponse findByIdWithChildren(Long id) {
        return new CategoryResponse(categoryRepository.findByIdWithChildren(id));
    }

    @Transactional
    public Long createCategory(Long id, String name) {
        if (id == null) {
            return categoryRepository.save(Category.create(null, name)).getId();
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("카테고리를 찾을 수 없습니다."));

        return categoryRepository.save(Category.create(category, name)).getId();

    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
