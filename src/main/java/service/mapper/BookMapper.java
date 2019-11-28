package service.mapper;

import dao.entity.BookEntity;
import domain.Book;

import java.util.stream.Collectors;

public class BookMapper {
    private final AuthorMapper authorMapper;

    public BookMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    public Book mapBookEntityToBook(BookEntity bookEntity) {
        return Book.builder()
                .withId(bookEntity.getId())
                .withName(bookEntity.getName())
                .withAuthors(bookEntity.getAuthors().stream()
                        .map(authorMapper::mapAuthorEntityToAuthor)
                        .collect(Collectors.toList()))
                .withDescription(bookEntity.getDescription())
                .withShelfId(bookEntity.getShelfId())
                .withIssueDate(bookEntity.getIssueDate())
                .withReturnDate(bookEntity.getReturnDate())
                .build();
    }

    public BookEntity mapBookToBookEntity(Book book) {
        return BookEntity.builder()
                .withId(book.getId())
                .withName(book.getName())
                .withDescription(book.getDescription())
                .withShelfId(book.getShelfId())
                .build();
    }
}
