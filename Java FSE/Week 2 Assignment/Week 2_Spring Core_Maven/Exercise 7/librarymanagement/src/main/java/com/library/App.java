package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("\nApplication starting...");
        BookService bs = (BookService) context.getBean("bookService");
        // Use bookService to verify the injection
        bs.someMethod();
        System.out.println("Application finished...\n");
    }
}
