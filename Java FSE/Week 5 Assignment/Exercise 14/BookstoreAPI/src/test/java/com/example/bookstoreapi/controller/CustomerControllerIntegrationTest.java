package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.entity.Customer;
import com.example.bookstoreapi.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setup() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testCreateCustomer() throws Exception {
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
        customer.setName("Subha Saha");
        customer.setEmail("subha@gmail.com");
        customer.setAddress("West Bengal");
        customerRepository.save(customer);

        mockMvc.perform(get("/customers/" + customer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Subha Saha"))
                .andExpect(jsonPath("$.email").value("subha@gmail.com"))
                .andExpect(jsonPath("$.address").value("West Bengal"));
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        mockMvc.perform(get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Subha Saha"));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer customer = new Customer();

        mockMvc.perform(put("/customers/" + customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Subha\",\"email\":\"subha@gmail.com\",\"address\":\"Kolkata\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Subha"))
                .andExpect(jsonPath("$.email").value("subha@gmail.com"))
                .andExpect(jsonPath("$.address").value("Kolkata"));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        Customer customer = new Customer();

        mockMvc.perform(delete("/customers/" + customer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<Customer> deletedCustomer = customerRepository.findById(customer.getId());
        assert (deletedCustomer.isEmpty());
    }
}
