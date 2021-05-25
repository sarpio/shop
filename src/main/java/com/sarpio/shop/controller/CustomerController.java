package com.sarpio.shop.controller;

import com.sarpio.shop.model.dto.CustomerDto;

import com.sarpio.shop.repository.cache.CustomerCache;
import com.sarpio.shop.service.CustomerService;
import com.sarpio.shop.error.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import static com.sarpio.shop.config.SecurityConfig.*;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    CustomerService customerService;
    CustomerCache customerCache;

    @GetMapping
    public List<CustomerDto> listAllCustomers() {
        List<CustomerDto> allCustomers = customerService.findAllCustomers();
        return allCustomers;
    }
    @PreAuthorize("hasRole('"+ SALES_ROLE +"')")
    @Operation(description = "find client by Id")
    @GetMapping("{id}")
    public CustomerDto findCustomerById(@PathVariable Long id) throws ResourceNotFoundException, InterruptedException {
        Optional<CustomerDto> customerResponse = customerCache.getCustomerResponse(id);
        if (customerResponse.isPresent()) return customerResponse.get();
        Thread.sleep(3000);
        return customerService.findCustomerById(id);
    }
    @PreAuthorize("hasRole('"+ SALES_ROLE +"')")
    @Operation(description = "Show all clients")
    @PostMapping
    public CustomerDto addNewCustomer(@RequestBody CustomerDto dto) {
        return customerService.addCustomer(dto);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('"+ ADMIN_ROLE +"')")
    @Operation(description = "Delete client by Id")
    public CustomerDto deleteCustomer(@PathVariable Long id) throws ResourceNotFoundException {
        return customerService.deleteCustomerById(id);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('"+ MANAGER_ROLE +"') || hasRole('"+ ADMIN_ROLE +"')")
    @Operation(description = "Update client by Id")
    public CustomerDto updateCustomerById(
            @PathVariable Long id,
            @Valid @RequestBody CustomerDto customerDto) throws ResourceNotFoundException {
        return customerService.editCustomerById(id, customerDto);
    }

}
