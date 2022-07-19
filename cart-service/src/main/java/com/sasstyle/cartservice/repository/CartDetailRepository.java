package com.sasstyle.cartservice.repository;

import com.sasstyle.cartservice.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    @Query("select cd from CartDetail cd join fetch cd.cart as c where c.userId=:userId AND cd.id=:cartDetailId")
    CartDetail findByUserIdAndId(@Param("userId") String userId, @Param("cartDetailId") Long cartDetailId);
}
