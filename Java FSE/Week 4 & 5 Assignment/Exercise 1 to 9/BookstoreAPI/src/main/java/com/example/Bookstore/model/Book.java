package com.example.Bookstore.model;


import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private Double price;
    private String isbn;
    private String publishedDate;  // Assuming you have this field

    @Version
    private Long version;
}
