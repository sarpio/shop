package com.sarpio.shop.repository;

import com.sarpio.shop.model.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, Long> {
    boolean existsById(Long id);
}
