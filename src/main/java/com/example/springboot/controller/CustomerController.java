package com.example.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.springboot.model.Customer;


@CrossOrigin
@RestController
@RequestMapping("api/v1/")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public List<Customer> getAllCustomers(){
		return customerService.getAllCustomers();
	}

	@PostMapping("/customers")
	public Customer createCustomer(@RequestBody Customer customer){
		return customerService.createCustomer(customer);
	}

	//Pagination below
	public Page<Customer> getPagedCustomers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
		return customerService.getPagedCustomers(page, size);
	}
	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
		Customer customer = customerService.getCustomerById(id).getBody();
		return ResponseEntity.ok(customer);
	}

	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer ){
		Customer newCustomer = customerService.updateCustomer(id, customer).getBody();
		newCustomer.setFirstName(customer.getFirstName());
		newCustomer.setLastName(customer.getLastName());
		newCustomer.setEmailId(customer.getEmailId());
		newCustomer.setAccountBalance(customer.getAccountBalance());

		Customer postedCustomer = customerService.createCustomer(newCustomer);
		return ResponseEntity.ok(postedCustomer);
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable Long id ){
		Customer customer = customerService.getCustomerById(id).getBody();
		customerService.deleteCustomer(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
