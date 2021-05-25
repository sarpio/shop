package com.sarpio.shop.controller;

import com.sarpio.shop.model.dto.OrdersDto;
import com.sarpio.shop.model.dto.post.SaveOrdersDto;
import com.sarpio.shop.service.OrdersService;
import com.sarpio.shop.error.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.sarpio.shop.config.SecurityConfig.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrdersController {

    OrdersService orderService;

    @PreAuthorize("hasRole('" + SALES_ROLE + "')")
    @Operation(description = "Show all orders")
    @GetMapping
    public List<OrdersDto> findAllOrders() {
        return orderService.findAllOrders();
    }

    @PreAuthorize("hasRole('" + SALES_ROLE + "')")
    @Operation(description = "Show order by Id")
    @GetMapping("/{id}")
    public OrdersDto findOrderById(@PathVariable Long id) throws ResourceNotFoundException {
        return orderService.findOrdersById(id);
    }

    @PreAuthorize("hasRole('" + MANAGER_ROLE + "')")
    @Operation(description = "Add new order")
    @PostMapping("/{customerId}")
    public SaveOrdersDto addNewOrder(@Valid
                                     @RequestBody SaveOrdersDto dto,
                                     @PathVariable Long customerId) {
        return orderService.createOrder(dto, customerId);
    }

    @PreAuthorize("hasRole('" + MANAGER_ROLE + "')")
    @Operation(description = "Update order by Id")
    @PutMapping("/{id}")
    public SaveOrdersDto editOrder(@PathVariable Long id,
                                   @Valid
                                   @RequestBody SaveOrdersDto dto) throws ResourceNotFoundException {
        dto.setId(id);
        return orderService.updateOrder(dto, id);
    }

    @PreAuthorize("hasRole('" + ADMIN_ROLE + "')")
    @Operation(description = "Delete order by Id")
    @DeleteMapping("/{id}")
    public OrdersDto deleteOrder(@PathVariable Long id) throws ResourceNotFoundException {
        return orderService.deleteOrderById(id);
    }

}
