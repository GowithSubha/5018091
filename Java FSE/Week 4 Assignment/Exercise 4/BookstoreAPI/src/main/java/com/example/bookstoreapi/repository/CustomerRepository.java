package com.example.bookstoreapi.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bookstoreapi.entity.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByNameContaining(String name);
    List<Customer> findByEmailContaining(String email);
    List<Customer> findByNameContainingAndEmailContaining(String name, String email);
}
