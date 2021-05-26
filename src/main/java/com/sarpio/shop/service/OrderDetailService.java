package com.sarpio.shop.service;

import com.sarpio.shop.model.OrderDetailEntity;
import com.sarpio.shop.model.OrdersEntity;
import com.sarpio.shop.model.ProductsEntity;
import com.sarpio.shop.model.dto.OrderDetailDto;
import com.sarpio.shop.model.dto.OrdersDto;
import com.sarpio.shop.model.dto.post.SaveOrderDetailDto;
import com.sarpio.shop.model.dto.post.SaveOrdersDto;
import com.sarpio.shop.repository.OrderDetailRepository;
import com.sarpio.shop.repository.OrdersRepository;
import com.sarpio.shop.repository.ProductsRepository;
import com.sarpio.shop.utils.EntityDtoMapper;
import com.sarpio.shop.error.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderDetailService {

    OrderDetailRepository detailRepository;
    ProductsRepository productsRepository;
    OrdersRepository ordersRepository;
    OrdersService ordersService;

    public List<OrderDetailDto> findAllDetails() {
        return detailRepository.findAll().stream()
                .map(EntityDtoMapper::map)
                .collect(Collectors.toList());
    }

    public OrderDetailDto findOrderDetailById(Long id) throws ResourceNotFoundException {
//        if (!detailRepository.existsById(id)) {
//            throw new ResourceNotFoundException("There is no item with given id: " + id);
//        }
        OrderDetailEntity orderDetailEntityById = detailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Detail order with id: " + id));
        return EntityDtoMapper.map(orderDetailEntityById);
    }

    public OrderDetailDto deleteDetailById(Long id) throws ResourceNotFoundException {
//        if (!detailRepository.existsById(id)) {
//            throw new ResourceNotFoundException("Order detail with given id: " + id + " not exists");
//        }
        OrderDetailEntity delEntity = detailRepository.findById(id).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("There is not order detail with id:" + id));
        detailRepository.deleteById(id);
        updateOrderTotalValue(delEntity.getOrdersEntity().getId());
        return EntityDtoMapper.map(delEntity);
    }

    public SaveOrderDetailDto saveDetail(SaveOrderDetailDto dto) throws ResourceNotFoundException {
//        boolean isOrderPresent = ordersRepository.findById(dto.getOrderId()).isPresent();
//        if (!isOrderPresent) throw new ResourceNotFoundException("No valid order present with id: " + dto.getOrderId());
//        boolean isProductPresent = productsRepository.findById(dto.getProductId()).isPresent();
//        if (!isProductPresent) throw new ResourceNotFoundException("No valid Product present with id: " + dto.getProductId());

        OrderDetailEntity entity = EntityDtoMapper.map(dto);
        ProductsEntity prodEntity = productsRepository.findById(dto.getProductId()).get();
        Double price = prodEntity.getPrice();
        entity.setTotal_price(price * entity.getQuantity());
        detailRepository.save(entity);
        updateOrderTotalValue(entity.getOrdersEntity().getId());
        return dto;
    }

    public SaveOrderDetailDto updateOrdersDetail(Long id, SaveOrderDetailDto dto) {
//        if (id == null) {
//            throw new ResourceNotFoundException("No id provided for edited Order detail");
//        }
        if (!detailRepository.existsById(id)) throw new ResourceNotFoundException("Invalid ID: " + id + " provided");
        OrderDetailEntity entity = OrderDetailEntity.builder().build();
        OrdersEntity order = OrdersEntity.builder().build();
        order.setId(dto.getOrderId());
        ProductsEntity product = ProductsEntity.builder().build();
        product.setId(dto.getProductId());
        entity.setId(id);
        dto.setId(id);
        entity.setOrdersEntity(order);
        entity.setProductsEntity(product);
        entity.setQuantity(dto.getQuantity());
        Double total = orderDetailsTotalCalc(entity.getProductsEntity().getId(), entity.getQuantity());
        entity.setTotal_price(total);
        detailRepository.save(entity);
        updateOrderTotalValue(entity.getOrdersEntity().getId());
        return dto;
    }

    public Double orderDetailsTotalCalc(Long productId, Long quantity) {
        Double unitPrice = productsRepository.findById(productId).get().getPrice();
        return unitPrice * quantity;
    }

    public void updateOrderTotalValue(Long orderId) {
        OrdersEntity ordersEntity = ordersRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Incorrect order id: " + orderId));
        ordersEntity.setTotal(ordersService.getOrderTotal(orderId));
        ordersRepository.save(ordersEntity);


    }
}
