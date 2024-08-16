package com.example.Bookstore.model;

// Exercise 4.1: Implement a Customer model with fields for id, name, email, and password.
import lombok.Data;


@Data
public class Customer {
    private Long id;
    private String name;
    private String email;
    private String password;
}