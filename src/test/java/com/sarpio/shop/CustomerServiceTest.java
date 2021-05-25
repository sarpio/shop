package com.sarpio.shop;

import com.sarpio.shop.model.CustomersEntity;
import com.sarpio.shop.model.dto.CustomerDto;
import com.sarpio.shop.repository.CustomerRepository;
import com.sarpio.shop.repository.cache.CustomerCache;
import com.sarpio.shop.service.CustomerService;
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
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerCache customerCache;

    @InjectMocks
    private CustomerService customerService;

    @Before
    public void init() {
        Mockito.when(customerRepository.findAll()).thenReturn(prepareCustomerSList());
    }

    @Test
    public void shouldReturnListOfCustomers() {
        List<CustomerDto> customers = customerService.findAllCustomers();
        Assertions.assertNotNull(customers);
        Assertions.assertFalse(customers.isEmpty());
        Assertions.assertEquals(3, customers.size());
    }

    @Test
    public void shouldReturnCustomerById() {
        //given
        Mockito.when(customerCache.getCustomerResponse(1L)).thenReturn(Optional.empty());
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(CustomersEntity.builder()
                .id(1L)
                .first_name("Jan")
                .last_name("Kowalski")
                .email("jk@email.com")
                .phone("400-500-600")
                .build()));
        //when
        CustomerDto dtoCustomer = customerService.findCustomerById(1L);

        //then
        Assertions.assertNotNull(dtoCustomer);
        Assertions.assertEquals(1L, (long) dtoCustomer.getId());
        Assertions.assertEquals("Jan", dtoCustomer.getFirst_name());
    }

    @Test
    public void shouldProperAddCustomer() {
        CustomersEntity customersEntity = CustomersEntity.builder()
                .id(1L)
                .first_name("Jan")
                .last_name("Kowalski")
                .email("jk@email.com")
                .phone("400-500-600")
                .build();
        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customersEntity);
        CustomerDto customerDto = CustomerDto.builder()
                .id(1L)
                .first_name("Jan")
                .last_name("Kowalski")
                .email("jk@email.com")
                .phone("400-500-600")
                .build();
        CustomerDto customerDto1 = customerService.addCustomer(customerDto);
        Assertions.assertNotNull(customerDto1);
        Assertions.assertEquals("Jan", customerDto1.getFirst_name());
        Assertions.assertEquals(1L, customerDto1.getId());
    }

    @Test
    public void shouldEditCustomer() {
        CustomersEntity customersEntity = CustomersEntity.builder()
                .id(1L)
                .first_name("Jan")
                .last_name("Kowalski")
                .email("jk@email.com")
                .phone("400-500-600")
                .build();
        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customersEntity);
        CustomerDto customerDto = CustomerDto.builder()
                .id(1L)
                .first_name("Jan")
                .last_name("Kowalski")
                .email("jk@email.com")
                .phone("400-500-600")
                .build();
        CustomerDto customerDto1 = customerService.editCustomerById(1L, customerDto);
        Assertions.assertNotNull(customerDto1);
        Assertions.assertEquals("Kowalski", customerDto1.getLast_name());
        Assertions.assertEquals(1L, customerDto1.getId());
    }


    private List<CustomersEntity> prepareCustomerSList() {
        List<CustomersEntity> customers = new ArrayList<>();
        customers.add(CustomersEntity.builder()
                .id(1L)
                .first_name("Jan")
                .last_name("Kowalski")
                .email("jk@email.com")
                .phone("400-500-600")
                .build());
        customers.add(CustomersEntity.builder()
                .id(1L)
                .first_name("Adam")
                .last_name("Nowak")
                .email("an@email.com")
                .phone("401-501-601")
                .build());
        customers.add(CustomersEntity.builder()
                .id(1L)
                .first_name("Robert")
                .last_name("Adamski")
                .email("ra@email.com")
                .phone("402-502-602")
                .build());
        return customers;
    }
}
