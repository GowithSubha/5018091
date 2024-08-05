package com.library;

public class BookService {
    private BookRepository bookRepository;

    // constructor injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // setter injection
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void someMethod() {
        System.out.println("BookService.someMethod() : Doing something...");
        bookRepository.bookSave();
    }
}
