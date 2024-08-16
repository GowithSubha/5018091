package com.example.Bookstore.controller;

import com.example.Bookstore.dto.CustomerDTO;
import com.example.Bookstore.model.Customer;
import com.example.Bookstore.mapper.CustomerMapper;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController {

    private List<Customer> customerList = new ArrayList<>();
    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    // Get all customers
    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerList.stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    // Get a customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerList.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        return customer.map(value -> ResponseEntity.ok(customerMapper.customerToCustomerDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new customer
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId((long) (customerList.size() + 1)); // Generate ID
        customerList.add(customer);
        return ResponseEntity.ok(customerMapper.customerToCustomerDTO(customer));
    }

    // Update an existing customer by ID
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        Optional<Customer> existingCustomer = customerList.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (existingCustomer.isPresent()) {
            Customer updatedCustomer = existingCustomer.get();
            updatedCustomer.setName(customerDTO.getName());
            updatedCustomer.setEmail(customerDTO.getEmail());
            updatedCustomer.setPassword(customerDTO.getPassword());
            return ResponseEntity.ok(customerMapper.customerToCustomerDTO(updatedCustomer));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a customer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        boolean removed = customerList.removeIf(customer -> customer.getId().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Register a new customer using form data
    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> registerCustomer(@RequestParam("name") String name,
                                                        @RequestParam("email") String email,
                                                        @RequestParam("password") String password) {
        Customer customer = new Customer();
        customer.setId((long) (customerList.size() + 1)); // Generate ID
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);

        customerList.add(customer);
        return ResponseEntity.ok(customerMapper.customerToCustomerDTO(customer));
    }
}
