package com.sasstyle.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Entity
public class Category extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Column(nullable = false)
    private String name;
    private int depth;

    @OneToMany(mappedBy = "parent", cascade = ALL)
    private List<Category> children = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = ALL)
    private List<Product> products = new ArrayList<>();

    //== 비지니스 메서드 ==//
    @Builder
    public Category(Long id, Category category, String name) {
        this.id = id;
        this.parent = category;
        this.name = name;
        this.depth = category == null ? 0 : incrementDepth(category);
    }

    private int incrementDepth(Category category) {
        return category.getDepth() + 1;
    }
}
