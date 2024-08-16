package com.example.Bookstore.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;


@Data
public class BookDTO {
    private Long id;

    @NotNull(message = "Title cannot be null")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;

    @NotNull(message = "Author cannot be null")
    @Size(min = 2, max = 100, message = "Author must be between 2 and 100 characters")
    private String author;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "ISBN cannot be null")
    @Size(min = 10, max = 50, message = "ISBN must be between 10 and 50 characters")
    private String isbn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String publishedDate;  // Assuming you have this field
}
