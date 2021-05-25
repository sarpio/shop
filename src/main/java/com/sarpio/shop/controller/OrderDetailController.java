package com.sarpio.shop.controller;

import com.sarpio.shop.model.dto.OrderDetailDto;
import com.sarpio.shop.model.dto.post.SaveOrderDetailDto;
import com.sarpio.shop.service.OrderDetailService;
import com.sarpio.shop.error.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

import static com.sarpio.shop.config.SecurityConfig.*;

@RestController
@RequestMapping("/ordersdetail")
@AllArgsConstructor
public class OrderDetailController {

    OrderDetailService orderDetailService;

    @GetMapping
    @PreAuthorize("hasRole('"+ SALES_ROLE +"')")
    @Operation(description = "Show all order details")
    public List<OrderDetailDto> allDetails() {
        List<OrderDetailDto> all = orderDetailService.findAllDetails();
        return all;
    }
    @PreAuthorize("hasRole('"+ SALES_ROLE +"')")
    @Operation(description = "Show order details by Id")
    @GetMapping("/{id}")
    public OrderDetailDto findDetailById(@PathVariable @Valid Long id) throws ResourceNotFoundException {
        return orderDetailService.findOrderDetailById(id);
    }
    @PreAuthorize("hasRole('"+ ADMIN_ROLE +"')")
    @Operation(description = "Delete order details by Id")
    @DeleteMapping("{id}")
    public OrderDetailDto deleteDetailById(@PathVariable @Valid Long id) throws ResourceNotFoundException {
        return orderDetailService.deleteDetailById(id);
    }
    @PreAuthorize("hasRole('"+ MANAGER_ROLE +"')")
    @Operation(description = "Update order details by Id")
    @PutMapping("{id}")
    public SaveOrderDetailDto updateDetailById(@PathVariable @Valid Long id, @RequestBody SaveOrderDetailDto detail) throws ResourceNotFoundException {
        return orderDetailService.updateOrdersDetail(id, detail);

    }

    @PostMapping("/")
    public SaveOrderDetailDto addOrderDetail(@RequestBody @Valid SaveOrderDetailDto detail) throws ResourceNotFoundException {
        return orderDetailService.saveDetail(detail);
    }
}
