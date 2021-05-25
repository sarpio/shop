package com.sarpio.shop;

import com.sarpio.shop.model.CategoryEntity;
import com.sarpio.shop.model.CustomersEntity;
import com.sarpio.shop.model.ProductsEntity;
import com.sarpio.shop.model.dto.ProductsDto;
import com.sarpio.shop.repository.ProductsRepository;
import com.sarpio.shop.repository.cache.ProductCache;
import com.sarpio.shop.service.ProductsService;
import lombok.Builder;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.websocket.ClientEndpoint;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ShopApplicationTests {

    @Mock
    private final ProductsRepository productsRepositoryMock;

    @Mock
    private final ProductCache productCache;

    @InjectMocks
    private final ProductsService productsService;

    @Before
    public void init() {
        Mockito.when(productsRepositoryMock.findAll()).thenReturn(prepareProductList());
    }

    @Test
    // given
    public void shouldReturnListOfProductsConvertedToDto() {
        List<ProductsDto> products = productsService.findAllProducts();
        Assertions.assertNotNull(products);
        Assertions.assertTrue(products.size()==3);
        Assertions.assertFalse(products.isEmpty());
    }

    private  List<ProductsEntity> prepareProductList() {
        List<ProductsEntity> productsEntities = new ArrayList<>();
        productsEntities.add(ProductsEntity.builder()
                .id(1L)
                .name("Product 1")
                .description("Description 1")
                .price(220.01)
                .categoryEntity(CategoryEntity.builder()
                        .id(1L).build())
                .build());
        productsEntities.add(ProductsEntity.builder()
                .id(2L)
                .name("Product 2")
                .description("Description 2")
                .price(120.01)
                .categoryEntity(CategoryEntity.builder()
                        .id(2L).build())
                .build());
        productsEntities.add(ProductsEntity.builder()
                .id(3L)
                .name("Product 3")
                .description("Description 3")
                .price(88.12)
                .categoryEntity(CategoryEntity.builder()
                        .id(3L).build())
                .build());
        return productsEntities;
    }

}
