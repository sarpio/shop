package com.sarpio.shop.repository.cache;

import com.sarpio.shop.config.CacheConfig;
import com.sarpio.shop.model.dto.ProductsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductCache {

    @Cacheable(key = "#id",
            cacheManager = CacheConfig.PRODUCT_RESPONSE_CACHE_MANAGER,
            cacheNames = CacheConfig.PRODUCT_RESPONSE_CACHE_NAME)
    public Optional<ProductsDto> getProductResponse(Long id) {
        return Optional.empty();
    }

    @CachePut(key = "#productsDto.id",
            cacheManager = CacheConfig.PRODUCT_RESPONSE_CACHE_MANAGER,
            cacheNames = CacheConfig.PRODUCT_RESPONSE_CACHE_NAME)
    public ProductsDto saveResponseInCache(ProductsDto productsDto) {
        return productsDto;
    }

    @CacheEvict(key = "#id",
            cacheManager = CacheConfig.PRODUCT_RESPONSE_CACHE_MANAGER,
            cacheNames = CacheConfig.PRODUCT_RESPONSE_CACHE_NAME)
    public void deleteProductResponseFromCahce(Long id) {

    }
}
