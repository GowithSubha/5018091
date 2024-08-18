package com.example.Bookstore.controller;

import com.example.Bookstore.assembler.BookResourceAssembler;
import com.example.Bookstore.dto.BookDTO;
import com.example.Bookstore.exception.BookNotFoundException;
import com.example.Bookstore.mapper.BookMapper;
import com.example.Bookstore.model.Book;
import com.example.Bookstore.metrics.CustomMetricsConfig;

import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.MeterRegistry;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5372")
@RequestMapping("/books")
@Validated
@Tag(name = "Book", description = "The Book API. Contains operations like get all books, get book by ID, create book, update book, delete book etc.")
public class BookController {

    private List<Book> bookList = new ArrayList<>();
    private final BookMapper bookMapper = BookMapper.INSTANCE;
    private final BookResourceAssembler bookResourceAssembler;
    private final CustomMetricsConfig customMetricsConfig;
    private final MeterRegistry meterRegistry;

    @Autowired
    public BookController(BookResourceAssembler bookResourceAssembler, CustomMetricsConfig customMetricsConfig,
            MeterRegistry meterRegistry) {
        this.bookResourceAssembler = bookResourceAssembler;
        this.customMetricsConfig = customMetricsConfig;
        this.meterRegistry = meterRegistry;
    }

    // Get all books
    @Operation(summary = "Get all books", description = "Returns a list of all books")
    @GetMapping
    public List<EntityModel<BookDTO>> getAllBooks(
            @RequestHeader(value = HttpHeaders.ACCEPT, defaultValue = MediaType.APPLICATION_JSON_VALUE) String acceptHeader) {
        customMetricsConfig.incrementBookCounter();
        return bookList.stream()
                .map(book -> bookResourceAssembler.toModel(bookMapper.bookToBookDTO(book)))
                .collect(Collectors.toList());
    }

    // Get a book by ID
    @Operation(summary = "Get a book by ID", description = "Returns a single book")
    @GetMapping("/{id}")
    public EntityModel<BookDTO> getBookById(
            @Parameter(description = "ID of the book to be retrieved") @PathVariable Long id) {
        Book book = findBookById(id);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + id + " is not found.");
        }
        return bookResourceAssembler.toModel(bookMapper.bookToBookDTO(book));
    }

    // Create a new book with custom headers
    @Operation(summary = "Create a new book", description = "Adds a new book to the bookstore")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EntityModel<BookDTO>> createBookWithHeaders(@Valid @RequestBody BookDTO bookDTO) {
        Timer.Sample sample = customMetricsConfig.startBookCreationTimer(meterRegistry);
        Book book = bookMapper.bookDTOToBook(bookDTO);
        bookList.add(book);

        customMetricsConfig.stopBookCreationTimer(sample);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "Book has been successfully created");

        EntityModel<BookDTO> bookResource = bookResourceAssembler.toModel(bookDTO);
        return new ResponseEntity<>(bookResource, headers, HttpStatus.CREATED);
    }

    // Update an existing book by ID
    @Operation(summary = "Update an existing book", description = "Updates the details of an existing book")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<BookDTO>> updateBook(
            @Parameter(description = "ID of the book to be updated") @PathVariable Long id,
            @Parameter(description = "Updated book object") @RequestBody @Valid BookDTO updatedBookDTO) {
        Book book = findBookById(id);
        if (book != null) {
            book.setTitle(updatedBookDTO.getTitle());
            book.setAuthor(updatedBookDTO.getAuthor());
            book.setPrice(updatedBookDTO.getPrice());
            book.setIsbn(updatedBookDTO.getIsbn());
            book.setPublishedDate(updatedBookDTO.getPublishedDate());
            return ResponseEntity.ok(bookResourceAssembler.toModel(bookMapper.bookToBookDTO(book)));
        }
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + id + " is not found.");
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a book by ID
    @Operation(summary = "Delete a book", description = "Deletes a book by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID of the book to be deleted") @PathVariable Long id) {
        bookList.removeIf(book -> book.getId().equals(id));
        return ResponseEntity.noContent().build();
    }

    // Search books by title and author using query parameters
    @Operation(summary = "Search books", description = "Search books by title and author")
    @GetMapping("/search")
    public List<EntityModel<BookDTO>> searchBooks(
            @Parameter(description = "Enter Title of the book") @RequestParam(required = false) String title,
            @Parameter(description = "Enter Author of the book") @RequestParam(required = false) String author) {
        return bookList.stream()
                .filter(book -> (title == null || book.getTitle().contains(title)) &&
                        (author == null || book.getAuthor().contains(author)))
                .map(book -> bookResourceAssembler.toModel(bookMapper.bookToBookDTO(book)))
                .collect(Collectors.toList());
    }

    // Helper methods for conversion
    private Book findBookById(Long id) {
        return bookList.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
