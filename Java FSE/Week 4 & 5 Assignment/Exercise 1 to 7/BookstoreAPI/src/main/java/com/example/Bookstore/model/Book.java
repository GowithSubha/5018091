package com.example.Bookstore.model;


import lombok.Data;

@Data
public class Book {
    private Long id;
    private String title;
    private String author;
    private Double price;
    private String isbn;
    private String publishedDate;  // Assuming you have this field
}
