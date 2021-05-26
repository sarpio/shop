package com.sarpio.shop;

import com.sarpio.shop.model.*;
import com.sarpio.shop.model.dto.OrdersDto;
import com.sarpio.shop.model.dto.post.SaveOrdersDto;
import com.sarpio.shop.repository.OrderDetailRepository;
import com.sarpio.shop.repository.OrdersRepository;
import com.sarpio.shop.service.OrdersService;
import com.sarpio.shop.utils.EntityDtoMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private OrderDetailEntity orderDetailEntity;

    @InjectMocks
    private OrdersService ordersService;

    @Before
    public void init() {
        Mockito.when(ordersRepository.findAll()).thenReturn(prepareOrdersEntities());
        Mockito.when(orderDetailRepository.findAllByOrdersEntityId(1L)).thenReturn(prepareOrderDetailEntities());
    }


    @Test
    public void shouldReturnProperAllOrders() {
        List<OrdersDto> ordersDto = ordersService.findAllOrders();
        Assertions.assertNotNull(ordersDto);
        Assertions.assertFalse(ordersDto.isEmpty());
        Assertions.assertEquals(3, ordersDto.size());
    }

    @Test
    public void shouldReturnOrderById() {
        //given
        Mockito.when(ordersRepository.findById(1L)).thenReturn(Optional.of(OrdersEntity.builder()
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
                .build()));
        //when
        OrdersDto ordersDto = ordersService.findOrdersById(1L);
        //then
        Assertions.assertNotNull(ordersDto);
        Assertions.assertEquals(1L, ordersDto.getId());
        Assertions.assertEquals(1234L, ordersDto.getNumber());
    }

    @Test
    public void shouldProperAddOrder() {
        OrdersEntity ordersEntity = OrdersEntity.builder()
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
                .build();
        Mockito.when(ordersRepository.save(Mockito.any())).thenReturn(ordersEntity);
        SaveOrdersDto saveOrdersDto = SaveOrdersDto.builder()
                .id(1L)
                .number(1234L)
                .build();
        SaveOrdersDto saveOrdersDto1 = ordersService.createOrder(saveOrdersDto, 1L);
        Assertions.assertNotNull(saveOrdersDto1);
        Assertions.assertEquals(1234L, saveOrdersDto1.getNumber());
        Assertions.assertEquals(1L, saveOrdersDto1.getId());
    }

    @Test
    public void shouldProperEditOrder() {
        OrdersEntity ordersEntity = OrdersEntity.builder()
                .id(1L)
                .number(1234L)
                .status(OrdersStatus.OPEN)
                .date(LocalDate.now())
                .total(100.0)
                .orderDetailEntities(prepareOrderDetailsSet())
                .customersEntity(CustomersEntity.builder()
                        .id(1L)
                        .first_name("Jan")
                        .last_name("Kowalski")
                        .email("email@dot.com")
                        .phone("505-606-707")
                        .address("Address of persone who is int his record 1")
                        .build()
                )
                .build();
        Mockito.when(ordersRepository.save(Mockito.any())).thenReturn(ordersEntity);
//        Mockito.when(orderDetailRepository.save(Mockito.any())).thenReturn(orderDetailEntity);
        Mockito.when(ordersRepository.findById(1l)).thenReturn(Optional.ofNullable(ordersEntity));
        SaveOrdersDto saveOrdersDto = SaveOrdersDto.builder()
                .id(1L)
                .number(1234L)
                .status(OrdersStatus.PAYD)
                .build();
        OrdersEntity entity = EntityDtoMapper.map(ordersService.updateOrder(saveOrdersDto, 1L));
        Assertions.assertNotNull(saveOrdersDto);
        Assertions.assertEquals(1234L, entity.getNumber());
        Assertions.assertEquals(OrdersStatus.PAYD, entity.getStatus());
        Assertions.assertEquals(1L, entity.getId());
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
                .build());
        return orders;
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

    private Set<OrderDetailEntity> prepareOrderDetailsSet() {
        Set<OrderDetailEntity> details = new HashSet<>();
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
}
