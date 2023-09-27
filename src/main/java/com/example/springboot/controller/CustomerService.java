package com.example.springboot.controller;

import com.example.springboot.exception.ResourceNotFound;
import com.example.springboot.model.Customer;
import com.example.springboot.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }


    public Customer createCustomer(@RequestBody Customer customer){
        return customerRepository.save(customer);
    }


    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Customer doesn't exist in Database"));
        return ResponseEntity.ok(customer);
    }
    //Pagination below
    public Page<Customer> getPagedCustomers(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return customerRepository.findAll(pageable);
    }

    public ResponseEntity<List<Customer>> getCustomerByName(@PathVariable String Name){
        return new ResponseEntity<>(customerRepository.findByFirstName(Name), HttpStatus.OK);
    }


    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer ){
        Customer newCustomer = customerRepository.findById( id ).orElseThrow(() -> new ResourceNotFound("Customer doesn't exist"));
        newCustomer.setFirstName(customer.getFirstName());
        newCustomer.setLastName(customer.getLastName());
        newCustomer.setEmailId(customer.getEmailId());
        newCustomer.setAccountBalance(customer.getAccountBalance());

        Customer postedCustomer = customerRepository.save(newCustomer);
        return ResponseEntity.ok(postedCustomer);
    }


    public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable Long id ){
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Customer not found"));
        customerRepository.delete(customer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
