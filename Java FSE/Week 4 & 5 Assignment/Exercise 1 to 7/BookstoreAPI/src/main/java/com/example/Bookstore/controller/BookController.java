package com.example.Bookstore.controller;


import com.example.Bookstore.exception.BookNotFoundException;
import com.example.Bookstore.model.Book;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Bookstore.dto.BookDTO;
import com.example.Bookstore.mapper.BookMapper;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private List<Book> bookList = new ArrayList<>();
    private final BookMapper bookMapper = BookMapper.INSTANCE;

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookList.stream()
                .map(bookMapper::bookToBookDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        for (Book book : bookList) {
            if (book.getId().equals(id)) {
                return ResponseEntity.ok(bookMapper.bookToBookDTO(book));
            }
        }
        throw new BookNotFoundException("Book with ID " + id + " is not found.");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDTO> createBookWithHeaders(@RequestBody BookDTO bookDTO) {
        Book book = bookMapper.bookDTOToBook(bookDTO);
        bookList.add(book);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "Book has been successfully created");

        return new ResponseEntity<>(bookMapper.bookToBookDTO(book), headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO updatedBookDTO) {
        for (Book book : bookList) {
            if (book.getId().equals(id)) {
                book.setTitle(updatedBookDTO.getTitle());
                book.setAuthor(updatedBookDTO.getAuthor());
                book.setPrice(updatedBookDTO.getPrice());
                book.setIsbn(updatedBookDTO.getIsbn());
                book.setPublishedDate(updatedBookDTO.getPublishedDate());
                return ResponseEntity.ok(bookMapper.bookToBookDTO(book));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookList.removeIf(book -> book.getId().equals(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<BookDTO> searchBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String author) {
        return bookList.stream()
                .filter(book -> (title == null || book.getTitle().contains(title)) &&
                        (author == null || book.getAuthor().contains(author)))
                .map(bookMapper::bookToBookDTO)
                .collect(Collectors.toList());
    }




}
