package com.example.bookstoreapi.controller;


import com.example.bookstoreapi.dto.CustomerDTO;
import com.example.bookstoreapi.mapper.CustomerMapper;
import com.example.bookstoreapi.entity.Customer;
import com.example.bookstoreapi.exception.CustomerNotFoundException;
import com.example.bookstoreapi.repository.CustomerRepository;

import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;



import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CollectionModel<EntityModel<CustomerDTO>>> getAllCustomers() {
        List<CustomerDTO> customers = customerRepository.findAll().stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());

        for (CustomerDTO customerDTO : customers) {
            int id = customerDTO.getId();
            customerDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(id)).withSelfRel());
        }

        CollectionModel<EntityModel<CustomerDTO>> collectionModel = CollectionModel.of(
                customers.stream().map(EntityModel::of).collect(Collectors.toList())
        );
        collectionModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getAllCustomers()).withSelfRel());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "All customers found at /customers");
        return ResponseEntity.ok().headers(headers).body(collectionModel);
    }

    @GetMapping(value="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<CustomerDTO>> getCustomerById(@PathVariable int id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }

        CustomerDTO customerDTO = customerMapper.toDTO(customerRepository.findById(id).get());
        customerDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getAllCustomers()).withRel("All Customers"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Customer found at /customers/" + id);
        return ResponseEntity.ok().headers(headers).body(EntityModel.of(customerDTO));
    }

    @GetMapping(value="/search", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
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

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<CustomerDTO>> addCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.toDTO(savedCustomer);

        savedCustomerDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(savedCustomerDTO.getId())).withSelfRel());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Customer created at /customers/" + savedCustomerDTO.getId());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(EntityModel.of(savedCustomerDTO));
    }

    @PostMapping(value="/register", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<CustomerDTO>> registerCustomer(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("address") String address) {


        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setAddress(address);

        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.toDTO(savedCustomer);
        savedCustomerDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(savedCustomerDTO.getId())).withSelfRel());


        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Customer created at /customers/" + savedCustomer.getId());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(EntityModel.of(savedCustomerDTO));

    }

    @PutMapping(value="/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<CustomerDTO>> updateCustomer(@PathVariable int id, @Valid @RequestBody CustomerDTO customerDTO) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }

        Customer customer = customerMapper.toEntity(customerDTO);
        customer.setId(id);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.toDTO(savedCustomer);
        savedCustomerDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(savedCustomerDTO.getId())).withSelfRel());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Customer updated at /customers/" + savedCustomerDTO.getId());
        return ResponseEntity.ok().headers(headers).body(EntityModel.of(savedCustomerDTO));
    }

    @DeleteMapping(value="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
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
