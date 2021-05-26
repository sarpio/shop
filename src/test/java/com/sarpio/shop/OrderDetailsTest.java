package com.sarpio.shop;

import com.sarpio.shop.model.CategoryEntity;
import com.sarpio.shop.model.OrderDetailEntity;
import com.sarpio.shop.model.ProductsEntity;
import com.sarpio.shop.model.dto.OrderDetailDto;
import com.sarpio.shop.model.dto.post.SaveOrderDetailDto;
import com.sarpio.shop.repository.OrderDetailRepository;
import com.sarpio.shop.repository.ProductsRepository;
import com.sarpio.shop.service.OrderDetailService;
import com.sarpio.shop.service.ProductsService;
import com.sarpio.shop.utils.EntityDtoMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class OrderDetailsTest {


    @Mock
    private OrderDetailRepository orderDetailRepository;
    @Mock
    private ProductsRepository productsRepository;
    @Mock
    private OrderDetailEntity orderDetailEntity;
    @InjectMocks
    private OrderDetailService orderDetailService;
    @InjectMocks
    private ProductsService productsService;
    @Before
    public void init() {
        Mockito.when(orderDetailRepository.findAll()).thenReturn(prepareOrderDetailEntities());
        Mockito.when(productsRepository.findAll()).thenReturn(prepareProductEntities());
    }

    @Test
    public void shouldReturnProperAllOrders() {
        List<OrderDetailDto> details = orderDetailService.findAllDetails();
        Assertions.assertNotNull(details);
        Assertions.assertFalse(details.isEmpty());
        Assertions.assertEquals(3, details.size());
    }

    @Test
    public void shouldReturnOrderDetailsById() {
        //given
        Mockito.when(orderDetailRepository.findById(1L)).thenReturn(Optional.of(OrderDetailEntity.builder()
                .id(1L)
                .total_price(200.0)
                .quantity(2L)
                .productsEntity(ProductsEntity.builder()
                        .id(1L)
                        .build())
                .build()));
        //when
        OrderDetailDto orderDetailDto = orderDetailService.findOrderDetailById(1L);

        //then
        Assertions.assertNotNull(orderDetailDto);
        Assertions.assertEquals(1L, orderDetailDto.getId());
        Assertions.assertEquals(200, orderDetailDto.getTotal_price());
    }

    @Test
    public void shouldProperAddDetailOrders() {
        ProductsEntity productsEntity = new ProductsEntity();
        productsEntity.setId(1L);
        productsEntity.setPrice(100.0);
        productsEntity.setName("Product name");
        productsEntity.setDescription("Description of the product");
        OrderDetailEntity orderDetailEntity = OrderDetailEntity.builder()
                .id(1L)
                .total_price(100.0)
                .productsEntity(productsEntity)
                .build();

        Mockito.when(orderDetailRepository.save(Mockito.any())).thenReturn(orderDetailEntity);
        Mockito.when(productsRepository.save(Mockito.any())).thenReturn(productsEntity);
        Mockito.when(productsRepository.findById(1L)).thenReturn(Optional.of(prepareProductEntities().get(1)));
        SaveOrderDetailDto orderDetailDto = SaveOrderDetailDto.builder()
                .id(1L)
                .productId(1L)
                .orderId(1L)
                .quantity(3L)
                .build();
        SaveOrderDetailDto orderDetailDto1 = orderDetailService.saveDetail(EntityDtoMapper.mapSave(orderDetailEntity));
        Assertions.assertNotNull(orderDetailDto1);
        Assertions.assertEquals(1L, orderDetailDto1.getId());
        Assertions.assertEquals(3L, orderDetailDto1.getQuantity());
    }


    private List<OrderDetailEntity> prepareOrderDetailEntities() {
        List<OrderDetailEntity> details = new ArrayList<>();

        details.add(OrderDetailEntity.builder()
                .id(1L)
                .quantity(1L)
                .productsEntity(ProductsEntity.builder()
                        .price(100.0)
                        .build())
                .total_price(100.0)
                .build());
        details.add(OrderDetailEntity.builder()
                .id(2L)
                .quantity(2L)
                .productsEntity(ProductsEntity.builder()
                        .price(100.0)
                        .build())
                .total_price(200.0)
                .build());
        details.add(OrderDetailEntity.builder()
                .id(3L)
                .quantity(3L)
                .productsEntity(ProductsEntity.builder()
                        .price(100.0)
                        .build())
                .total_price(300.0)
                .build());
        return details;
    }
    private List<ProductsEntity> prepareProductEntities() {
        List<ProductsEntity> products = new ArrayList<>();
        products.add(ProductsEntity.builder()
                .id(1L)
                .name("Product 1")
                .description("Description 1")
                .price(220.01)
                .categoryEntity(prepareCategoryEntities().get(0))
                .build());
        products.add(ProductsEntity.builder()
                .id(2L)
                .name("Product 2")
                .description("Description 2")
                .price(120.01)
                .categoryEntity(prepareCategoryEntities().get(1))
                .build());
        products.add(ProductsEntity.builder()
                .id(3L)
                .name("Product 3")
                .description("Description 3")
                .price(88.12)
                .categoryEntity(prepareCategoryEntities().get(2))
                .build());
        return products;
    }
    private List<CategoryEntity> prepareCategoryEntities() {
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        categoryEntities.add(CategoryEntity.builder()
                .id(1L)
                .name("Category 1")
                .build());
        categoryEntities.add(CategoryEntity.builder()
                .id(2L)
                .name("Category 2")
                .build());
        categoryEntities.add(CategoryEntity.builder()
                .id(3L)
                .name("Category 3")
                .build());
        return categoryEntities;
    }
}
