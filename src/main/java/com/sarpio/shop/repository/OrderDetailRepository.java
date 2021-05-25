package com.sarpio.shop.repository;

import com.sarpio.shop.model.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {
    boolean existsById(Long id);

    List<OrderDetailEntity> findAllByOrdersEntityId(Long id);

}
