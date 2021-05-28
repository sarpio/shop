package com.sarpio.shop;

import com.sarpio.shop.model.*;
import com.sarpio.shop.model.dto.OrderDetailDto;
import com.sarpio.shop.model.dto.post.SaveOrderDetailDto;
import com.sarpio.shop.repository.OrderDetailRepository;
import com.sarpio.shop.repository.OrdersRepository;
import com.sarpio.shop.repository.ProductsRepository;
import com.sarpio.shop.service.OrderDetailService;
import com.sarpio.shop.service.OrdersService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class OrderDetailServiceTest {

    @Mock
    private OrderDetailRepository orderDetailRepository;
    @Mock
    private ProductsRepository productsRepository;
    @Mock
    private OrdersRepository ordersRepository;
    @Mock
    private OrdersService ordersService;

    @InjectMocks
    private OrderDetailService orderDetailService;

    @Before
    public void init() {
        Mockito.when(orderDetailRepository.findAll()).thenReturn(prepareOrderDetailEntities());
    }

    @Test
    public void shouldReturnProperListOfOrderDetails() {
        List<OrderDetailDto> orderDetailDtos = orderDetailService.findAllDetails();
        Assertions.assertNotNull(orderDetailDtos);
        Assertions.assertFalse(orderDetailDtos.isEmpty());
        Assertions.assertEquals(3, orderDetailDtos.size());
    }

    @Test
    public void shouldReturnProperOrderDetailById() {
        //given
        Mockito.when(orderDetailRepository.findById(1L)).thenReturn(Optional.of(OrderDetailEntity.builder()
                .id(1L)
                .quantity(10L)
                .total_price(100.0)
                .build()));
        //When
        OrderDetailDto orderDetailDto = orderDetailService.findOrderDetailById(1L);
        //Then
        Assertions.assertNotNull(orderDetailDto);
        Assertions.assertEquals(1L, orderDetailDto.getId());
        Assertions.assertEquals(100, orderDetailDto.getTotal_price());

    }

    @Test
    public void shouldProperAddOrderDetail() {
        SaveOrderDetailDto detailDto = SaveOrderDetailDto.builder()
                .id(1L)
                .quantity(3L)
                .productId(1L)
                .orderId(1L)
                .build();
        OrdersEntity ordersEntity = prepareOrdersEntities().stream().findFirst().get();
        ProductsEntity productsEntity = prepareProductEntities().stream().findFirst().get();
        Mockito.when(productsRepository.findById(detailDto.getProductId())).thenReturn(Optional.of(productsEntity));
        Mockito.when(ordersRepository.findById(detailDto.getOrderId())).thenReturn(Optional.of(ordersEntity));
        SaveOrderDetailDto saveOrderDetailDto = orderDetailService.saveDetail(detailDto);
        Assertions.assertNotNull(saveOrderDetailDto);
        Assertions.assertEquals(1L, saveOrderDetailDto.getId());
        Assertions.assertEquals(3L, saveOrderDetailDto.getQuantity());
    }

    @Test
    public void shouldProperEditOrderDetails() {
        SaveOrderDetailDto detailDto = SaveOrderDetailDto.builder()
                .id(1L)
                .quantity(3L)
                .productId(1L)
                .orderId(1L)
                .build();
        OrdersEntity ordersEntity = prepareOrdersEntities().stream().findFirst().get();
        ProductsEntity productsEntity = prepareProductEntities().stream().findFirst().get();
        Mockito.when(productsRepository.findById(detailDto.getProductId())).thenReturn(Optional.of(productsEntity));
        Mockito.when(ordersRepository.findById(detailDto.getOrderId())).thenReturn(Optional.of(ordersEntity));
        SaveOrderDetailDto saveOrderDetailDto = orderDetailService.updateOrdersDetail(1L, detailDto);
        Assertions.assertNotNull(saveOrderDetailDto);
        Assertions.assertEquals(1L, saveOrderDetailDto.getId());
        Assertions.assertEquals(3L, saveOrderDetailDto.getQuantity());
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

    private List<OrdersEntity> prepareOrdersEntities() {
        List<OrdersEntity> orders = new ArrayList<>();
        orders.add(OrdersEntity.builder()
                .id(1L)
                .number(1234L)
                .date(LocalDate.now())
                .customersEntity(CustomersEntity.builder()
                        .id(1L)
                        .first_name("Jan")
                        .last_name("Kowalski")
                        .email("jk@dot.com")
                        .phone("555-666-777")
                        .address("Home Addres Street Number 1")
                        .build())
                .total(101.0)
                .status(OrdersStatus.COMPLETE)
                .build());
        orders.add(OrdersEntity.builder()
                .id(2L)
                .number(2345L)
                .date(LocalDate.now())
                .customersEntity(CustomersEntity.builder()
                        .id(1L)
                        .first_name("Adam")
                        .last_name("nowak")
                        .email("an@dot.com")
                        .phone("534-654-765")
                        .address("Home Addres Street Number 2")
                        .build())
                .total(102.0)
                .status(OrdersStatus.COMPLETE)
                .build());
        orders.add(OrdersEntity.builder()
                .id(3L)
                .number(3456L)
                .date(LocalDate.now())
                .customersEntity(CustomersEntity.builder()
                        .id(1L)
                        .first_name("Stefan")
                        .last_name("Kozioł")
                        .email("sk@google.com")
                        .phone("606-707-808")
                        .address("Home Addres Street Number 3")
                        .build())
                .total(103.0)
                .status(OrdersStatus.COMPLETE)
                .build());
        return orders;
    }
}
