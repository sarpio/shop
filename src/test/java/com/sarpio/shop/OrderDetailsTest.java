package com.sarpio.shop;

import com.sarpio.shop.model.OrderDetailEntity;
import com.sarpio.shop.model.ProductsEntity;
import com.sarpio.shop.model.dto.OrderDetailDto;
import com.sarpio.shop.model.dto.OrdersDto;
import com.sarpio.shop.repository.OrderDetailRepository;
import com.sarpio.shop.service.OrderDetailService;
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
    private OrderDetailEntity orderDetailEntity;

    @InjectMocks
    private OrderDetailService orderDetailService;

    @Before
    public void init() {
        Mockito.when(orderDetailRepository.findAll()).thenReturn(prepareOrderDetailEntities());
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

    public void shouldProperAddDetailorders() {

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
}
