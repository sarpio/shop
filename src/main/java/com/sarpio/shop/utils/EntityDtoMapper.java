package com.sarpio.shop.utils;

import com.sarpio.shop.model.*;
import com.sarpio.shop.model.dto.*;
import com.sarpio.shop.model.dto.post.SaveOrderDetailDto;
import com.sarpio.shop.model.dto.post.SaveProductDto;
import org.springframework.beans.BeanUtils;
import java.util.stream.Collectors;

public class EntityDtoMapper {

    public static CategoryDto map(CategoryEntity entity) {
        CategoryDto dto = new CategoryDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public static CategoryEntity map(CategoryDto dto) {
        CategoryEntity entity = new CategoryEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public static ProductsDto map(ProductsEntity entity) {
        ProductsDto dto = ProductsDto.builder().build();
        BeanUtils.copyProperties(entity, dto);
        if (entity.getCategoryEntity() != null) {
            dto.setCategory(EntityDtoMapper.map(entity.getCategoryEntity()));
        }
        return dto;
    }

    public static ProductsEntity map(SaveProductDto dto) {
        ProductsEntity entity = ProductsEntity.builder().build();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public static CustomerDto map(CustomersEntity entity) {
        CustomerDto dto = CustomerDto.builder().build();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public static CustomersEntity map(CustomerDto dto) {
        CustomersEntity entity = CustomersEntity.builder().build();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public static OrdersDto map(OrdersEntity entity) {
        OrdersDto dto = new OrdersDto();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getCustomersEntity() != null) {
            dto.setCustomerDto(EntityDtoMapper.map(entity.getCustomersEntity()));
        }
        if (entity.getOrderDetailEntities() !=null) {
            dto.setOrderDetailsSet(entity.getOrderDetailEntities().stream()
            .map(EntityDtoMapper::map).collect(Collectors.toSet()));
        }
        return dto;
    }

    public static OrdersEntity map(OrdersDto dto) {
        OrdersEntity entity = OrdersEntity.builder().build();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public static OrderDetailDto map(OrderDetailEntity entity) {
        OrderDetailDto dto = OrderDetailDto.builder().build();
        if (entity.getProductsEntity() != null) {
            dto.setProductsDto(EntityDtoMapper.map(entity.getProductsEntity()));
        }
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public static OrderDetailEntity map(OrderDetailDto dto) {
        OrderDetailEntity entity = OrderDetailEntity.builder().build();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public static OrderDetailEntity map(SaveOrderDetailDto saveDto) {
        ProductsEntity productsEntity = ProductsEntity.builder().build();
        productsEntity.setId(saveDto.getProductId());
        OrdersEntity ordersEntity = OrdersEntity.builder().build();
        ordersEntity.setId(saveDto.getOrderId());
        OrderDetailEntity detailEntity = OrderDetailEntity.builder().build();
        detailEntity.setProductsEntity(productsEntity);
        detailEntity.setOrdersEntity(ordersEntity);
        detailEntity.setQuantity(saveDto.getQuantity());
        return detailEntity;
    }
}
