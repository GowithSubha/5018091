package com.example.Bookstore.controller;

import com.example.Bookstore.assembler.CustomerResourceAssembler;
import com.example.Bookstore.dto.CustomerDTO;
import com.example.Bookstore.exception.CustomerNotFoundException;
import com.example.Bookstore.mapper.CustomerMapper;
import com.example.Bookstore.model.Customer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5372")
@RequestMapping("/customers")
@Validated
public class CustomerController {

    private List<Customer> customerList = new ArrayList<>();
    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    private final CustomerResourceAssembler customerResourceAssembler;

    @Autowired
    public CustomerController(CustomerResourceAssembler customerResourceAssembler) {
        this.customerResourceAssembler = customerResourceAssembler;
    }

    // Get all customers
    @GetMapping
    public List<EntityModel<CustomerDTO>> getAllCustomers(@RequestHeader(value = HttpHeaders.ACCEPT, defaultValue = MediaType.APPLICATION_JSON_VALUE) String acceptHeader) {
        return customerList.stream()
                .map(customer -> customerResourceAssembler.toModel(customerMapper.customerToCustomerDTO(customer)))
                .collect(Collectors.toList());
    }

    // Get a customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CustomerDTO>> getCustomerById(@PathVariable Long id) {
        Customer customer = findCustomerById(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " is not found.");
        }
        return ResponseEntity.ok(customerResourceAssembler.toModel(customerMapper.customerToCustomerDTO(customer)));
    }

    // Create a new customer
    @PostMapping
    public ResponseEntity<EntityModel<CustomerDTO>> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId((long) (customerList.size() + 1)); // Generate ID
        customerList.add(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "Customer has been successfully created");

        EntityModel<CustomerDTO> customerResource = customerResourceAssembler.toModel(customerDTO);
        return new ResponseEntity<>(customerResource, headers, HttpStatus.CREATED);
    }

    // Update an existing customer by ID
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CustomerDTO>> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerDTO customerDTO) {
        Customer existingCustomer = findCustomerById(id);
        if (existingCustomer != null) {
            existingCustomer.setName(customerDTO.getName());
            existingCustomer.setEmail(customerDTO.getEmail());
            existingCustomer.setPassword(customerDTO.getPassword());
            return ResponseEntity.ok(customerResourceAssembler.toModel(customerMapper.customerToCustomerDTO(existingCustomer)));
        }
        return ResponseEntity.notFound().build();
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
    public ResponseEntity<EntityModel<CustomerDTO>> registerCustomer(@RequestParam("name") String name,
                                                                     @RequestParam("email") String email,
                                                                     @RequestParam("password") String password) {
        Customer customer = new Customer();
        customer.setId((long) (customerList.size() + 1)); // Generate ID
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);

        customerList.add(customer);
        return ResponseEntity.ok(customerResourceAssembler.toModel(customerMapper.customerToCustomerDTO(customer)));
    }

    // Helper methods for conversion
    private Customer findCustomerById(Long id) {
        return customerList.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
