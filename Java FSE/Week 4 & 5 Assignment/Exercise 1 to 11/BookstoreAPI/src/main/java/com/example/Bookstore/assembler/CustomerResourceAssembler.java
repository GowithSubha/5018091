package com.example.Bookstore.assembler;

import com.example.Bookstore.controller.CustomerController;
import com.example.Bookstore.dto.CustomerDTO;
import com.example.Bookstore.model.Customer;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CustomerResourceAssembler {

    public EntityModel<CustomerDTO> toModel(CustomerDTO customerDTO) {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(customerDTO.getId())).withSelfRel();
        // Add other links if needed
        return EntityModel.of(customerDTO, selfLink);
    }
}