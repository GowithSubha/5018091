package com.example.Bookstore.controller;

// Exercise 4.1: Implement a CustomerController with an endpoint to create a customer.
import com.example.Bookstore.model.Customer;
import com.example.Bookstore.dto.CustomerDTO;
import com.example.Bookstore.mapper.CustomerMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private List<Customer> customerList = new ArrayList<>();
    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerList.stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customerList.add(customer);
        return ResponseEntity.ok(customerMapper.customerToCustomerDTO(customer));
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> registerCustomer(@RequestParam("name") String name,
                                                        @RequestParam("email") String email,
                                                        @RequestParam("password") String password) {
        Customer customer = new Customer();
        customer.setId((long) (customerList.size() + 1));
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);

        customerList.add(customer);
        return ResponseEntity.ok(customerMapper.customerToCustomerDTO(customer));
    }


}

