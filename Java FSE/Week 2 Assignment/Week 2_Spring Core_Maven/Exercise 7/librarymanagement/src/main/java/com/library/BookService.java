package com.library;

public class BookService {
    private BookRepository bookRepository;

    // constructor injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("Constructor injection done... ");

    }

    // setter injection
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("Setter injection done... ");
    }

    public void someMethod() {
        // verify the injection
        System.out.println("BookService is working with BookRepository: " + bookRepository);
    }
}

