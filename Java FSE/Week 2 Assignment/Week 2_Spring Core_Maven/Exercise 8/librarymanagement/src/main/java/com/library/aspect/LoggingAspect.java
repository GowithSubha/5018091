package com.library.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.library.BookService.*(..))")
    public void logBefore() {
        System.out.println("LoggingAspect.logBefore() : Method is about to be executed");
    }

    @After("execution(* com.library.BookService.*(..))")
    public void logAfter() {
        System.out.println("LoggingAspect.logAfter() : Method execution completed");
    }
}
