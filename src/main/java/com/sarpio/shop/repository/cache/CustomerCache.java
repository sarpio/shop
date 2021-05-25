package com.sarpio.shop.repository.cache;

import com.sarpio.shop.config.CacheConfig;
import com.sarpio.shop.model.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerCache {

    @Cacheable(key = "#id",
            cacheManager = CacheConfig.CUSTOMER_RESPONSE_CACHE_MANAGER,
            cacheNames = CacheConfig.CUSTOMER_RESPONSE_CACHE_NAME)
    public Optional<CustomerDto> getCustomerResponse(Long id) {
        return Optional.empty();
    }

    @CachePut(key = "#customerDto.id",
            cacheManager = CacheConfig.CUSTOMER_RESPONSE_CACHE_MANAGER,
            cacheNames = CacheConfig.CUSTOMER_RESPONSE_CACHE_NAME)

    public CustomerDto saveResponseInCache(CustomerDto customerDto) {
        return customerDto;
    }

    @CacheEvict(key = "#id",
            cacheManager = CacheConfig.CUSTOMER_RESPONSE_CACHE_MANAGER,
            cacheNames = CacheConfig.CUSTOMER_RESPONSE_CACHE_NAME)
    public void deleteCustomerResponseFromCahce(Long id) {
    }

}
