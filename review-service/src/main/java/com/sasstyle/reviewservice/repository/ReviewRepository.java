package com.sasstyle.reviewservice.repository;

import com.sasstyle.reviewservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewQueryRepository {
}
