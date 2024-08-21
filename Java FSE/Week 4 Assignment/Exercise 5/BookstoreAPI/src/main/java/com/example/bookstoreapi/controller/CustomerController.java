package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.entity.Customer;
import com.example.bookstoreapi.exception.CustomerNotFoundException;
import com.example.bookstoreapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "All customers found at /customers");
        return ResponseEntity.ok().headers(headers).body(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Customer found at /customers/" + id);
        return ResponseEntity.ok().headers(headers).body(customer);

    }

    @GetMapping("/search")
    public List<Customer> searchCustomers(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) String email) {
        if (name != null && email != null) {
            return customerRepository.findByNameContainingAndEmailContaining(name, email);
        } else if (name != null) {
            return customerRepository.findByNameContaining(name);
        } else if (email != null) {
            return customerRepository.findByEmailContaining(email);
        } else {
            return customerRepository.findAll();
        }
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {

            Customer savedCustomer = customerRepository.save(customer);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "Customer created at /customers/" + savedCustomer.getId());
            return ResponseEntity.ok().headers(headers).body(savedCustomer);

    }

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("address") String address) {

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setAddress(address);

        Customer savedCustomer = customerRepository.save(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Customer created at /customers/" + savedCustomer.getId());
        return ResponseEntity.ok().headers(headers).body(savedCustomer);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }

        customer.setId(id);
        Customer updatedCustomer = customerRepository.save(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Customer updated at /customers/" + id);
        return ResponseEntity.ok().headers(headers).body(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }

        customerRepository.deleteById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Customer deleted at /customers/" + id);
        return ResponseEntity.noContent().headers(headers).build();

    }


}
