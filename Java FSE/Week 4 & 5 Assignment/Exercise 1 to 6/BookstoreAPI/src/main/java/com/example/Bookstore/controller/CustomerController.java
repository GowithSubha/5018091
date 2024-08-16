package com.example.Bookstore.controller;

// Exercise 4.1: Implement a CustomerController with an endpoint to create a customer.
import com.example.Bookstore.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private List<Customer> customerList = new ArrayList<>();

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerList;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customerList.add(customer);
        return ResponseEntity.ok(customer);
    }

//    Exercise 4.2: Implement an endpoint to process form data for customer registrations.
    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password) {
        Customer customer = new Customer();
        customer.setId((long) (customerList.size() + 1));
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);

        customerList.add(customer);
        return ResponseEntity.ok(customer);
    }


}

