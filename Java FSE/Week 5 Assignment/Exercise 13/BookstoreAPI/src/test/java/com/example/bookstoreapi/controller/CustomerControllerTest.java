package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.controller.CustomerController;
import com.example.bookstoreapi.entity.Customer;
import com.example.bookstoreapi.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void testCreateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Subha Saha");
        customer.setEmail("subha@gmail.com");
        customer.setAddress("West Bengal");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Subha Saha\",\"email\":\"subha@gmail.com\",\"address\":\"West Bengal\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Subha Saha"))
                .andExpect(jsonPath("$.email").value("subha@gmail.com"))
                .andExpect(jsonPath("$.address").value("West Bengal"));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Subha Saha");
        customer.setEmail("subha@gmail.com");
        customer.setAddress("West Bengal");

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Subha Saha"))
                .andExpect(jsonPath("$.email").value("subha@gmail.com"))
                .andExpect(jsonPath("$.address").value("West Bengal"));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer existingCustomer = new Customer();
        existingCustomer.setId(1);
        existingCustomer.setName("Subha Saha");
        existingCustomer.setEmail("subha@gmail.com");
        existingCustomer.setAddress("West Bengal");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1);
        updatedCustomer.setName("Jane Doe");
        updatedCustomer.setEmail("jane@example.com");
        updatedCustomer.setAddress("456 Elm St");

        when(customerRepository.findById(1)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        mockMvc.perform(put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Jane Doe\",\"email\":\"jane@example.com\",\"address\":\"456 Elm St\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane@example.com"))
                .andExpect(jsonPath("$.address").value("456 Elm St"));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Subha Saha");
        customer.setEmail("subha@gmail.com");
        customer.setAddress("West Bengal");

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        Mockito.doNothing().when(customerRepository).deleteById(1);

        mockMvc.perform(delete("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
