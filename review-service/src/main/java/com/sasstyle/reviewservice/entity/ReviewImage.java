package com.sasstyle.reviewservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class ReviewImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "review_image_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false)
    private String imageUrl;

    public ReviewImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
