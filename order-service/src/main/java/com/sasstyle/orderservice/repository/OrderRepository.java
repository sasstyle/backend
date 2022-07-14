package com.sasstyle.orderservice.repository;

import com.sasstyle.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select distinct o from Order o join fetch o.orderDetails od where o.userId=:userId order by o.orderDate desc")
    List<Order> findAllByUserId(@Param("userId") String userId);

    Order findByUserIdAndId(String userId, Long id);
}
