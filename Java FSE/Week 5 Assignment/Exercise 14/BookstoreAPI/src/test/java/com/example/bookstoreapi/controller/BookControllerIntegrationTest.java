package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.entity.Book;
import com.example.bookstoreapi.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testCreateBook() throws Exception {
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
        book.setTitle("Software Engineering");
        book.setAuthor("Rajiv Mal");
        book.setPrice(895);
        book.setIsbn("978-0134685991");
        bookRepository.save(book);

        mockMvc.perform(get("/books/" + book.getId())
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
        book1.setTitle("Software Engineering");
        book1.setAuthor("Rajiv Mal");
        book1.setPrice(895);
        book1.setIsbn("978-0134685991");

        Book book2 = new Book();
        book2.setTitle("Verbal English");
        book2.setAuthor("R. S. Aggarwal");
        book2.setPrice(495);
        book2.setIsbn("978-0132350884");

        bookRepository.save(book1);
        bookRepository.save(book2);

        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Software Engineering"))
                .andExpect(jsonPath("$[1].title").value("Verbal English"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book book = new Book();

        mockMvc.perform(put("/books/" + book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Software Engineering, 3rd Edition\",\"author\":\"Rajiv Mal\",\"price\":50.00,\"isbn\":\"978-0134685991\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Software Engineering, 3rd Edition"))
                .andExpect(jsonPath("$.author").value("Rajiv Mal"))
                .andExpect(jsonPath("$.price").value(50.00))
                .andExpect(jsonPath("$.isbn").value("978-0134685991"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        Book book = new Book();


        mockMvc.perform(delete("/books/" + book.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<Book> deletedBook = bookRepository.findById(book.getId());
        assert (deletedBook.isEmpty());
    }
}
