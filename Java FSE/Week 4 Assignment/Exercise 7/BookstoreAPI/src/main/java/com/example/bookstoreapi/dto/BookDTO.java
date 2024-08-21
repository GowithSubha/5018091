package com.example.bookstoreapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookDTO {

    private int id;
    
    @JsonProperty("book_title")
    private String title;

    @JsonProperty("book_author")
    private String author;

    @JsonProperty("book_isbn")
    private String isbn;

    @JsonProperty("book_price")
    private double price;


}
