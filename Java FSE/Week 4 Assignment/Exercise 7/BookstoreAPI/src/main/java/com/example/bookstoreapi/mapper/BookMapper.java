package com.example.bookstoreapi.mapper;


import com.example.bookstoreapi.dto.BookDTO;
import com.example.bookstoreapi.entity.Book;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDTO toDTO (Book book);

    Book toEntity (BookDTO bookDTO);
}
