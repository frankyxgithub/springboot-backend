package com.example.springboot;

import com.example.springboot.controller.CustomerService;
import com.example.springboot.model.Customer;
import com.example.springboot.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerService.class)
public class CustomerServiceTest {
    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Test
    public void testGetAllCustomers(){

        when(customerRepository.findAll()).thenReturn(
                Stream.of(new Customer(1L,"Efe", "Okorobie", 350000.00, "efe@gmail.com"),
                        new Customer(2L, "John", "Enyinwa", 3500000, "john@gmail.com")).collect(Collectors.toList()));

        assertEquals(2, customerService.getAllCustomers().size());
    }

    @Test
    public void testCreateCustomers(){

        Customer customer = new Customer(1L,"Efe", "Okorobie", 350000.00, "efe@gmail.com");

        when(customerRepository.save(customer)).thenReturn(customer);

        assertEquals(customer, customerService.createCustomer(customer));

    }

    @Test
    public void testCustomerByName(){
        when(customerRepository.findByFirstName("Name")).thenReturn(
                Stream.of(new Customer(1L,"Efe", "Okorobie", 350000.00, "efe@gmail.com"),
                        new Customer(2L, "John", "Enyinwa", 3500000, "john@gmail.com")).collect(Collectors.toList()));

        assertEquals(2, customerService.getCustomerByName("Name").getBody().size());
    }
}