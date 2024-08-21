package com.example.bookstoreapi.controller;



import com.example.bookstoreapi.entity.Book;
import com.example.bookstoreapi.exception.BookNotFoundException;
import com.example.bookstoreapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "All books found at /books");
        return ResponseEntity.ok().headers(headers).body(books);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book =bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Book found at /books/" + id);
        return ResponseEntity.ok().headers(headers).body(book);

    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(required = false) String title,
                                  @RequestParam(required = false) String author) {
        if (title != null && author != null) {
            return bookRepository.findByTitleContainingAndAuthorContaining(title, author);
        } else if (title != null) {
            return bookRepository.findByTitleContaining(title);
        } else if (author != null) {
            return bookRepository.findByAuthorContaining(author);
        } else {
            return bookRepository.findAll();
        }
    }


    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {

        Book savedBook = bookRepository.save(book);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Book created at /books/" + savedBook.getId());
        return ResponseEntity.ok().headers(headers).body(savedBook);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }

        book.setId(id);
        Book updatedBook = bookRepository.save(book);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "Book updated at /books/" + id);
        return ResponseEntity.ok().headers(headers).body(updatedBook);
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
