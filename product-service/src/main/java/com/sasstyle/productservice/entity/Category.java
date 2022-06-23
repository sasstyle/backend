package com.sasstyle.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
    private String name;
    private int depth;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    //== 비지니스 메서드 ==//
    /**
     * 부모 카테고리 생성
     */
    public static Category create(String name) {
        return Category.builder()
                .name(name)
                .build();
    }

    /**
     * 자식 카테고리 생성
     */
    public static Category create(Category category, String name) {
        return Category.builder()
                .parent(category)
                .name(name)
                .depth(category.getDepth() + 1)
                .build();
    }
}
