package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService bs = (BookService) context.getBean("bookService");

        System.out.println("\nApplication starting...");
        bs.someMethod();
        System.out.println("Application finished...\n");

    }
}
