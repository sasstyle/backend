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
@Builder
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
    public static Category create(Category category, String name) {
        int depth = 0;

        if (category != null) {
            depth = category.getDepth() + 1;
        }

        return Category.builder()
                .parent(category)
                .name(name)
                .depth(depth)
                .children(new ArrayList<>())
                .products(new ArrayList<>())
                .build();
    }

    public void addChildren(Category category) {
        children.add(category);
        category.parent = this;
    }
}
