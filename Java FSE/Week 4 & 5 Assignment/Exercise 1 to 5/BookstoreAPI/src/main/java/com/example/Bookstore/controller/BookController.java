package com.example.Bookstore.controller;


import com.example.Bookstore.model.Book;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private List<Book> bookList = new ArrayList<>();

    @GetMapping
    public List<Book> getAllBooks() {
        return bookList;
    }

//    @PostMapping
//    public ResponseEntity<Book> addBook(@RequestBody Book book) {
//        bookList.add(book);
//        return ResponseEntity.ok(book);
//    }

//    Exercise 5.1: Use @ResponseStatus to customize HTTP status codes for your endpoints.
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)  // Sets the status code to 201 Created
//    public Book createBook(@RequestBody Book book) {
//        bookList.add(book);
//        return book;
//    }



    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        for (Book book : bookList) {
            if (book.getId().equals(id)) {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setPrice(updatedBook.getPrice());
                book.setIsbn(updatedBook.getIsbn());
                return ResponseEntity.ok(book);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookList.removeIf(book -> book.getId().equals(id));
        return ResponseEntity.noContent().build();
    }

//     Exercise 3.1: Implement an endpoint to fetch a book by its ID using a path variable.
//    @GetMapping("/{id}")
//    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
//        for (Book book : bookList) {
//            if (book.getId().equals(id)) {
//                return ResponseEntity.ok(book);
//            }
//        }
//        return ResponseEntity.notFound().build();
//    }


//    Exercise 5.1: Use @ResponseStatus to customize HTTP status codes for your endpoints.
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        for (Book book : bookList) {
            if (book.getId().equals(id)) {
                return ResponseEntity.ok(book);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Sets status code to 404 Not Found
    }

//    Exercise 5.2: Add custom headers to the response using ResponseEntity.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Book> createBookWithHeaders(@RequestBody Book book) {
        bookList.add(book);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "Book has been successfully created");

        return new ResponseEntity<>(book, headers, HttpStatus.CREATED);
    }

    //    Exercise 3.2: Implement an endpoint to filter books based on query parameters like title and author.
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String author) {
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : bookList) {
            if ((title == null || book.getTitle().contains(title)) && (author == null || book.getAuthor().contains(author))) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }

}
