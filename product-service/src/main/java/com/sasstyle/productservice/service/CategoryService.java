package com.sasstyle.productservice.service;

import com.sasstyle.productservice.controller.dto.CategoryResponse;
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
}
