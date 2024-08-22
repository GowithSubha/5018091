package com.example.bookstoreapi.mapper;

import com.example.bookstoreapi.dto.BookDTO;
import com.example.bookstoreapi.entity.Book;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-20T22:12:29+0530",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDTO toDTO(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDTO.BookDTOBuilder bookDTO = BookDTO.builder();

        bookDTO.id( book.getId() );
        bookDTO.title( book.getTitle() );
        bookDTO.author( book.getAuthor() );
        bookDTO.isbn( book.getIsbn() );
        bookDTO.price( book.getPrice() );

        return bookDTO.build();
    }

    @Override
    public Book toEntity(BookDTO bookDTO) {
        if ( bookDTO == null ) {
            return null;
        }

        Book.BookBuilder book = Book.builder();

        book.id( bookDTO.getId() );
        book.title( bookDTO.getTitle() );
        book.author( bookDTO.getAuthor() );
        book.price( bookDTO.getPrice() );
        book.isbn( bookDTO.getIsbn() );

        return book.build();
    }
}
