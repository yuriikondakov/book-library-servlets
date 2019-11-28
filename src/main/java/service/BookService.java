package service;

import domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book findById(Integer id);

    List<Book> findAll();

    boolean takeBook(Integer userId, Integer bookId);

    int getNumberOfRows();

    List<Book> getBooksPerPage(int currentPage, int recordsPerPage);

    List<Book> getBooksByUserId(Integer userId);

    boolean returnBook(Integer userId, Integer bookId);

    List<Book> getBooksWithSearchPerPage(int currentPage, int recordsPerPage, String searchField);

    Book addBook(Book book);
}
