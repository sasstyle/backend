package com.sasstyle.reviewservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Review extends BaseTime{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private Long productId;
    private String userId;
    private String reviewerName;

    private String content;

    @OneToMany(mappedBy = "review", cascade = ALL)
    @Builder.Default
    private List<ReviewImage> reviewImages = new ArrayList<>();
    private int rate;

    public void addReviewImage(ReviewImage reviewImage) {
        reviewImage.setReview(this);
        this.getReviewImages().add(reviewImage);
    }
}
