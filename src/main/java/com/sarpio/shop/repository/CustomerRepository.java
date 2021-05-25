package com.sarpio.shop.repository;

import com.sarpio.shop.model.CustomersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomersEntity, Long> {
    boolean existsById(Long id);
}
