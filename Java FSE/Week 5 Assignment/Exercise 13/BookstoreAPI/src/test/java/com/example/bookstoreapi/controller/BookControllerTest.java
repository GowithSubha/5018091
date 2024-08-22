package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.controller.BookController;
import com.example.bookstoreapi.entity.Book;
import com.example.bookstoreapi.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void testCreateBook() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Software Engineering");
        book.setAuthor("Rajiv Mal");
        book.setPrice(895);
        book.setIsbn("978-0134685991");

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Software Engineering\",\"author\":\"Rajiv Mal\",\"price\":895,\"isbn\":\"978-0134685991\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Software Engineering"))
                .andExpect(jsonPath("$.author").value("Rajiv Mal"))
                .andExpect(jsonPath("$.price").value(895))
                .andExpect(jsonPath("$.isbn").value("978-0134685991"));
    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Software Engineering");
        book.setAuthor("Rajiv Mal");
        book.setPrice(895);
        book.setIsbn("978-0134685991");

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Software Engineering"))
                .andExpect(jsonPath("$.author").value("Rajiv Mal"))
                .andExpect(jsonPath("$.price").value(895))
                .andExpect(jsonPath("$.isbn").value("978-0134685991"));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Software Engineering");
        book1.setAuthor("Rajiv Mal");
        book1.setPrice(895);
        book1.setIsbn("978-0134685991");

        Book book2 = new Book();
        book2.setId(2);
        book2.setTitle("Verbal English");
        book2.setAuthor("R. S. Aggarwal");
        book2.setPrice(400.00);
        book2.setIsbn("978-0132350884");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Software Engineering"))
                .andExpect(jsonPath("$[1].title").value("Verbal English"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book existingBook = new Book();
        existingBook.setId(1);
        existingBook.setTitle("Software Engineering");
        existingBook.setAuthor("Rajiv Mal");
        existingBook.setPrice(895);
        existingBook.setIsbn("978-0134685991");

        Book updatedBook = new Book();
        updatedBook.setId(1);
        updatedBook.setTitle("Software Engineering, 3rd Edition");
        updatedBook.setAuthor("Rajiv Mal");
        updatedBook.setPrice(1099);
        updatedBook.setIsbn("978-0134685991");

        when(bookRepository.findById(1)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Software Engineering, 3rd Edition\",\"author\":\"Rajiv Mal\",\"price\":1099,\"isbn\":\"978-0134685991\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Software Engineering, 3rd Edition"))
                .andExpect(jsonPath("$.author").value("Rajiv Mal"))
                .andExpect(jsonPath("$.price").value(1099))
                .andExpect(jsonPath("$.isbn").value("978-0134685991"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Software Engineering");
        book.setAuthor("Rajiv Mal");
        book.setPrice(895);
        book.setIsbn("978-0134685991");

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        Mockito.doNothing().when(bookRepository).deleteById(1);

        mockMvc.perform(delete("/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
