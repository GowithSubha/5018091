package com.example.Bookstore.assembler;

import com.example.Bookstore.controller.BookController;
import com.example.Bookstore.dto.BookDTO;
import com.example.Bookstore.model.Book;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class BookResourceAssembler {

    public EntityModel<BookDTO> toModel(BookDTO bookDTO) {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(bookDTO.getId())).withSelfRel();
        // Add other links if needed
        return EntityModel.of(bookDTO, selfLink);
    }
}