package com.example.bookstoreapi.controller;


import com.example.bookstoreapi.dto.CustomerDTO;
import com.example.bookstoreapi.mapper.CustomerMapper;
import com.example.bookstoreapi.entity.Customer;
import com.example.bookstoreapi.exception.CustomerNotFoundException;
import com.example.bookstoreapi.repository.CustomerRepository;

import io.micrometer.core.instrument.Timer;
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


import io.micrometer.core.instrument.MeterRegistry;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
@Validated
@Tag(name = "Customer", description = "Bookstore API implemented with Spring Boot RESTful service using springdoc-openapi and OpenAPI 3.")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MeterRegistry meterRegistry;

    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Operation(summary = "Get all customers", description = "Get all customers from the bookstore")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CollectionModel<EntityModel<CustomerDTO>>> getAllCustomers() {
        meterRegistry.counter("bookstoreapi.customers.getall").increment();
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

    @Operation(summary = "Get customer by ID", description = "Get a customer by ID from the bookstore")
    @GetMapping(value="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<CustomerDTO>> getCustomerById(@Parameter(description = "ID of the customer to be retrieved") @PathVariable int id) {
        meterRegistry.counter("bookstoreapi.customers.getbyid").increment();
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }

        CustomerDTO customerDTO = customerMapper.toDTO(customerRepository.findById(id).get());
        customerDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getAllCustomers()).withRel("All Customers"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Customer found at /customers/" + id);
        return ResponseEntity.ok().headers(headers).body(EntityModel.of(customerDTO));
    }

    @Operation(summary = "Search customers", description = "Search customers by name or email")
    @GetMapping(value="/search", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<CustomerDTO> searchCustomers(@Parameter(description = "Name of the customer to be searched") @RequestParam(required = false) String name,
                                            @Parameter(description = "Email of the customer to be searched") @RequestParam(required = false) String email) {
        meterRegistry.counter("bookstoreapi.customers.search").increment();
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

    @Operation(summary = "Add a customer", description = "Add a customer to the bookstore")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<CustomerDTO>> addCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Timer.Sample sample = Timer.start(meterRegistry);
        meterRegistry.counter("bookstoreapi.customers.create").increment();
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.toDTO(savedCustomer);

        savedCustomerDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(savedCustomerDTO.getId())).withSelfRel());
        sample.stop(meterRegistry.timer("bookstoreapi.customers.create.timer"));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Customer created at /customers/" + savedCustomerDTO.getId());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(EntityModel.of(savedCustomerDTO));
    }

    @Operation(summary = "Register a customer", description = "Register a customer to the bookstore")
    @PostMapping(value="/register", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<CustomerDTO>> registerCustomer(
            @Parameter(description = "Enter Name of the customer to be registered") @RequestParam("name") String name,
            @Parameter(description = "Enter Email of the customer to be registered") @RequestParam("email") String email,
            @Parameter(description = "Enter Address of the customer to be registered") @RequestParam("address") String address) {
        meterRegistry.counter("bookstoreapi.customers.register").increment();


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

    @Operation(summary = "Update customer by ID", description = "Update a customer in the bookstore by its ID")
    @PutMapping(value="/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<CustomerDTO>> updateCustomer(@Parameter(description = "ID of the customer to be updated") @PathVariable int id,@Parameter(description = "Customer object that needs to be updated")@Valid @RequestBody CustomerDTO customerDTO) {
        meterRegistry.counter("bookstoreapi.customers.update").increment();
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

    @Operation(summary = "Delete customer by ID", description = "Delete a customer from the bookstore by its ID")
    @DeleteMapping(value="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> deleteCustomer(@Parameter(description = "ID of the customer to be deleted") @PathVariable int id) {
        meterRegistry.counter("bookstoreapi.customers.delete").increment();
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }

        customerRepository.deleteById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Customer deleted at /customers/" + id);
        return ResponseEntity.noContent().headers(headers).build();

    }


}
