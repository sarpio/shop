package com.sarpio.shop.service;

import com.sarpio.shop.model.CustomersEntity;
import com.sarpio.shop.model.OrderDetailEntity;
import com.sarpio.shop.model.OrdersEntity;
import com.sarpio.shop.model.OrdersStatus;
import com.sarpio.shop.model.dto.OrdersDto;
import com.sarpio.shop.model.dto.post.SaveOrdersDto;
import com.sarpio.shop.repository.OrderDetailRepository;
import com.sarpio.shop.repository.OrdersRepository;
import com.sarpio.shop.utils.EntityDtoMapper;
import com.sarpio.shop.error.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrderDetailRepository detailRepository;

    public List<OrdersDto> findAllOrders() {
        return ordersRepository.findAll().stream()
                .map(EntityDtoMapper::map)
                .collect(Collectors.toList());
    }

    public OrdersDto findOrdersById(Long id) throws ResourceNotFoundException {
        if (!ordersRepository.existsById(id)) {
            throw new ResourceNotFoundException("There is no order with given id: " + id);
        }
        OrdersEntity ordersEntityById = ordersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No order with id: " + id));
        return EntityDtoMapper.map(ordersEntityById);
    }

    public SaveOrdersDto createOrder(SaveOrdersDto dto, Long customerId) {
        LocalDate date = LocalDate.now();
        OrdersEntity entity = OrdersEntity.builder().build();
        CustomersEntity customer = CustomersEntity.builder().build();
        customer.setId(customerId);
        entity.setCustomersEntity(customer);
        entity.setStatus(OrdersStatus.OPEN);
        entity.setDate(date);
        entity.setTotal(0.0);
        entity.setNumber(dto.getNumber());
        ordersRepository.save(entity);
        return dto;
    }

    public SaveOrdersDto updateOrder(SaveOrdersDto dto, Long orderId) {
        LocalDate date = LocalDate.now();
        OrdersEntity editedEntity = ordersRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("No order with id: " + orderId));
        editedEntity.setId(orderId);
        editedEntity.setStatus(dto.getStatus());
        editedEntity.setNumber(dto.getNumber());
        editedEntity.setDate(date);
        editedEntity.setTotal(getOrderTotal(orderId));
        ordersRepository.save(editedEntity);
        return dto;
    }

    public OrdersDto deleteOrderById(Long id) throws ResourceNotFoundException {
        if (!ordersRepository.existsById(id)) {
            throw new ResourceNotFoundException("There is no order with id: " + id);
        }
        OrdersEntity delOrder = ordersRepository.findById(id).stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No Order with id:" + id));
        ordersRepository.deleteById(id);
        return EntityDtoMapper.map(delOrder);
    }

    public Double getOrderTotal(Long id) {
        Double orderDetailTotalValue = detailRepository.findAllByOrdersEntityId(id)
                .stream()
                .map(OrderDetailEntity::getTotal_price)
                .reduce(Double::sum).orElseThrow(() -> new ResourceNotFoundException("Incorect order id: " + id));
        return orderDetailTotalValue;
    }

}
