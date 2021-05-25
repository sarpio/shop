package com.sarpio.shop.repository;

import com.sarpio.shop.model.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
    boolean existsById(Long id);

}
