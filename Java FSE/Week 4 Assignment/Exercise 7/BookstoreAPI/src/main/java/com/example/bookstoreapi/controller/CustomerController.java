package com.example.bookstoreapi.controller;


import com.example.bookstoreapi.dto.CustomerDTO;
import com.example.bookstoreapi.mapper.CustomerMapper;
import com.example.bookstoreapi.entity.Customer;
import com.example.bookstoreapi.exception.CustomerNotFoundException;
import com.example.bookstoreapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "All customers found at /customers");
        return ResponseEntity.ok().headers(headers).body(customerDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));

        CustomerDTO customerDTO = customerMapper.toDTO(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Customer found at /customers/" + id);
        return ResponseEntity.ok().headers(headers).body(customerDTO);
    }

    @GetMapping("/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String email) {
        List<Customer> customers;

        if (name != null && email != null) {
            customers = customerRepository.findByNameContainingAndEmailContaining(name, email);
        } else if (name != null) {
            customers = customerRepository.findByNameContaining(name);
        } else if (email != null) {
            customers = customerRepository.findByEmailContaining(email);
        } else {
            customers = customerRepository.findAll();
        }

        return customers.stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.toDTO(savedCustomer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Customer created at /customers/" + savedCustomer.getId());
        return ResponseEntity.ok().headers(headers).body(savedCustomerDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> registerCustomer(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("address") String address) {


        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setAddress(address);

        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.toDTO(savedCustomer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Customer created at /customers/" + savedCustomer.getId());
        return ResponseEntity.ok().headers(headers).body(savedCustomerDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable int id, @RequestBody CustomerDTO customerDTO) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }

        Customer customer = customerMapper.toEntity(customerDTO);
        customer.setId(id);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.toDTO(savedCustomer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Customer updated at /customers/" + id);
        return ResponseEntity.ok().headers(headers).body(savedCustomerDTO);
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
