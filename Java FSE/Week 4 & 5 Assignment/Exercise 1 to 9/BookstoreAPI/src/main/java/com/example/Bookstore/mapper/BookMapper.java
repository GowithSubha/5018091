package com.example.Bookstore.mapper;

import com.example.Bookstore.dto.BookDTO;
import com.example.Bookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "isbn", target = "isbn")
    @Mapping(source = "publishedDate", target = "publishedDate")
    BookDTO bookToBookDTO(Book book);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "isbn", target = "isbn")
    @Mapping(source = "publishedDate", target = "publishedDate")
    Book bookDTOToBook(BookDTO bookDTO);
}