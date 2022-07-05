package com.sasstyle.productservice.repository;

import com.sasstyle.productservice.entity.ProductProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductProfileRepository extends JpaRepository<ProductProfile, Long> {
}
