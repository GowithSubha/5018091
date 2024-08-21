package com.example.bookstoreapi.controller;


import com.example.bookstoreapi.dto.BookDTO;
import com.example.bookstoreapi.mapper.BookMapper;
import com.example.bookstoreapi.entity.Book;
import com.example.bookstoreapi.exception.BookNotFoundException;
import com.example.bookstoreapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOs = books.stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "All books found at /books");
        return ResponseEntity.ok().headers(headers).body(bookDTOs);
    }



    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));

        BookDTO bookDTO = bookMapper.toDTO(book);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Book found at /books/" + id);
        return ResponseEntity.ok().headers(headers).body(bookDTO);
    }

    @GetMapping("/search")
    public List<BookDTO> searchBooks(@RequestParam(required = false) String title,
                                     @RequestParam(required = false) String author) {
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


    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        BookDTO savedBookDTO = bookMapper.toDTO(savedBook);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Book created at /books/" + savedBook.getId());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(savedBookDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable int id, @RequestBody BookDTO bookDTO) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }

        Book book = bookMapper.toEntity(bookDTO);
        book.setId(id);
        Book savedBook = bookRepository.save(book);
        BookDTO savedBookDTO = bookMapper.toDTO(savedBook);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Book updated at /books/" + id);
        return ResponseEntity.ok().headers(headers).body(savedBookDTO);
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }

        bookRepository.deleteById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Book deleted at /books/" + id);
        return ResponseEntity.noContent().headers(headers).build();
    }
}
