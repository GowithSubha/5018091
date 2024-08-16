package com.example.Bookstore.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;


@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private Double price;
    private String isbn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String publishedDate;  // Assuming you have this field
}
