package com.example.bookstoreapi.controller;


import com.example.bookstoreapi.dto.BookDTO;
import com.example.bookstoreapi.mapper.BookMapper;
import com.example.bookstoreapi.entity.Book;
import com.example.bookstoreapi.exception.BookNotFoundException;
import com.example.bookstoreapi.repository.BookRepository;


import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;


import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@Validated
@Tag(name = "Book", description = "Bookstore API implemented with Spring Boot RESTful service using springdoc-openapi and OpenAPI 3.")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MeterRegistry meterRegistry;
    private Timer timer;


    private final BookMapper bookMapper = BookMapper.INSTANCE;

    @Operation(summary = "Get all books", description = "Get all books from the bookstore")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CollectionModel<EntityModel<BookDTO>>> getAllBooks() {
        meterRegistry.counter("bookstoreapi.books.getall").increment();

        List<BookDTO> books = bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());

        for (BookDTO bookDTO : books) {
            int id = bookDTO.getId();
            bookDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(id)).withSelfRel());
        }

        CollectionModel<EntityModel<BookDTO>> collectionModel = CollectionModel.of(
                books.stream().map(EntityModel::of).collect(Collectors.toList())
        );
        collectionModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks()).withSelfRel());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "All books found at /books");
        return ResponseEntity.ok().headers(headers).body(collectionModel);
    }


    @Operation(summary = "Get book by ID", description = "Get a book from the bookstore by its ID")
    @GetMapping(value ="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<BookDTO>> getBookById(@Parameter(description = "ID of the book to be retrieved") @PathVariable int id) {
        meterRegistry.counter("bookstoreapi.books.getbyid").increment();

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));
        BookDTO bookDTO = bookMapper.toDTO(book);
        bookDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(id)).withSelfRel());

        EntityModel<BookDTO> entityModel = EntityModel.of(bookDTO);
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks()).withRel("books"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Book found at /books/" + id);
        return ResponseEntity.ok().headers(headers).body(entityModel);
    }

    @Operation(summary = "Search books", description = "Search books in the bookstore by title or author")
    @GetMapping(value="/search", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<BookDTO> searchBooks(@Parameter(description = "Title of the book to be searched") @RequestParam(required = false) String title,
                                     @Parameter(description = "Author of the book to be searched") @RequestParam(required = false) String author) {
        meterRegistry.counter("bookstoreapi.books.search").increment();
        List<Book> books;

        if (title != null && author != null) {
            books = bookRepository.findByTitleContainingAndAuthorContaining(title, author);
        } else if (title != null) {
            books = bookRepository.findByTitleContaining(title);
        } else if (author != null) {
            books = bookRepository.findByAuthorContaining(author);
        } else {
            books = bookRepository.findAll();
        }

        return books.stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Create a new book", description = "Create a new book in the bookstore")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<BookDTO>> createBook(@Valid @Parameter(description = "Book to be created") @RequestBody BookDTO bookDTO) {
        meterRegistry.counter("bookstoreapi.books.create").increment();

        Book book = bookMapper.toEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        BookDTO savedBookDTO = bookMapper.toDTO(savedBook);
        savedBookDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(savedBookDTO.getId())).withSelfRel());

        EntityModel<BookDTO> entityModel = EntityModel.of(savedBookDTO);
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks()).withRel("books"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Book created at /books/" + savedBookDTO.getId());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(entityModel);
    }


    @Operation(summary = "Update book by ID", description = "Update a book in the bookstore by its ID")
    @PutMapping(value="/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<BookDTO>> updateBook(@Parameter(description = "ID of the book to be updated")@PathVariable int id,@Parameter(description = "Book to be updated") @Valid @RequestBody BookDTO bookDTO) {
        meterRegistry.counter("bookstoreapi.books.update").increment();
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        Book book = bookMapper.toEntity(bookDTO);
        book.setId(id);
        Book updatedBook = bookRepository.save(book);
        BookDTO updatedBookDTO = bookMapper.toDTO(updatedBook);
        updatedBookDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(updatedBookDTO.getId())).withSelfRel());

        EntityModel<BookDTO> entityModel = EntityModel.of(updatedBookDTO);
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks()).withRel("books"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Book updated at /books/" + id);
        return ResponseEntity.ok().headers(headers).body(entityModel);
    }



    @Operation(summary = "Delete book by ID", description = "Delete a book from the bookstore by its ID")
    @DeleteMapping(value="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> deleteBook(@Parameter(description = "ID of the book to be deleted") @PathVariable int id) {
        meterRegistry.counter("bookstoreapi.books.delete").increment();
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }

        bookRepository.deleteById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Book deleted at /books/" + id);
        return ResponseEntity.noContent().headers(headers).build();
    }
}
