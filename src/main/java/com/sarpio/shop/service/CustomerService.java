package com.sarpio.shop.service;

import com.sarpio.shop.model.CustomersEntity;
import com.sarpio.shop.model.dto.CustomerDto;
import com.sarpio.shop.repository.CustomerRepository;
import com.sarpio.shop.repository.cache.CustomerCache;
import com.sarpio.shop.utils.EntityDtoMapper;
import com.sarpio.shop.error.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerCache customerCache;

    public List<CustomerDto> findAllCustomers() {
        return customerRepository.findAll().stream()
                .map(EntityDtoMapper::map)
                .collect(Collectors.toList());
    }

    public CustomerDto findCustomerById(Long id) throws ResourceNotFoundException {
        Optional<CustomerDto> customerResponse = customerCache.getCustomerResponse(id);
        if (customerResponse.isPresent()) return customerResponse.get();
        CustomerDto customerDto = customerRepository.findById(id)
                .map(EntityDtoMapper::map)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Not found with id: " + id));
        customerCache.saveResponseInCache(customerDto);
        return customerDto;
    }

    public CustomerDto addCustomer(CustomerDto dto) {
        CustomersEntity entity = EntityDtoMapper.map(dto);
        CustomersEntity save = customerRepository.save(entity);
        return EntityDtoMapper.map(save);
    }

    public CustomerDto deleteCustomerById(Long id) throws ResourceNotFoundException {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("There is no customer with given id: " + id);
        }
        CustomersEntity delCustomer = customerRepository.findById(id).stream()
                .findFirst().orElseThrow(()->new ResourceNotFoundException("There is not customer with id " + id));
        customerRepository.deleteById(id);
        customerCache.deleteCustomerResponseFromCahce(id);
        return EntityDtoMapper.map(delCustomer);
    }

    public CustomerDto editCustomerById(Long id, CustomerDto dto) throws ResourceNotFoundException {
        CustomersEntity entity = EntityDtoMapper.map(dto);
        entity.setId(id);
        CustomersEntity save = customerRepository.save(entity);
        customerCache.deleteCustomerResponseFromCahce(id);
        return EntityDtoMapper.map(save);
    }
}
