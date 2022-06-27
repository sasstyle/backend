package com.sasstyle.productservice.service;

import com.sasstyle.productservice.controller.dto.CategoryResponse;
import com.sasstyle.productservice.entity.Category;
import com.sasstyle.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("카테고리를 찾을 수 없습니다."));
    }

    public List<CategoryResponse> findAllWithChildren() {
        return categoryRepository.findAllWithChildren();

    }

    public List<Long> findCategoryIds(Long id) {
        List<Long> categoryIds = categoryRepository.findCategoryIds(id);

        if (categoryIds.isEmpty()) {
            throw new NoSuchElementException("카테고리를 찾을 수 없습니다.");
        }

        return categoryIds;
    }

    @Transactional
    public Long createCategory(Long id, String name) {
        if (id == null) {
            return categoryRepository.save(Category.create(null, name)).getId();
        }

        Category category = findById(id);

        return categoryRepository.save(Category.create(category, name)).getId();

    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = findById(id);

        categoryRepository.delete(category);
    }
}
