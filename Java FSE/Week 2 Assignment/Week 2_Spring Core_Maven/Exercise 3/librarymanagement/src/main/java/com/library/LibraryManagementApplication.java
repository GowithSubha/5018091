package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LibraryManagementApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("\nApplication starting...");
        // Get the BookService bean using its name
        BookService bookService = (BookService) context.getBean("bookService");
        bookService.someMethod();
        System.out.println("Application finished...\n");
    }
}