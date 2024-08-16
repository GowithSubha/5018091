package com.example.Bookstore.model;

// Exercise 4.1: Implement a Customer model with fields for id, name, email, and password.
import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @Version
    private Long version;
}