package com.sasstyle.cartservice.repository;

import com.sasstyle.cartservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select distinct c from Cart c left join fetch c.cartDetails as cd where c.userId=:userId order by cd.updatedAt desc")
    Optional<Cart> findCart(@Param("userId") String userId);
}
