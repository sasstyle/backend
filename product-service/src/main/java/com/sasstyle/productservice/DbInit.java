package com.sasstyle.productservice;

import com.sasstyle.productservice.entity.Category;
import com.sasstyle.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class DbInit {

    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
        Category clothing = Category.create("의류");
        categoryRepository.save(clothing);

        Category top = Category.create(clothing, "상의");
        categoryRepository.save(top);

        Category sweaTshirt = Category.create(top, "맨투맨");
        Category hoodedTshirt = Category.create(top, "후드티셔츠");
        categoryRepository.save(sweaTshirt);
        categoryRepository.save(hoodedTshirt);
    }
}
