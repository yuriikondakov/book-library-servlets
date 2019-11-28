package dao;

import dao.entity.BookEntity;
import domain.Book;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface BookDao extends CrudDao<BookEntity, Integer> {

    int findRowsCount();

    boolean saveBookTracking(Integer userId, Integer bookId, LocalDate now);

    List<BookEntity> findBooksByUserId(Integer userId);

    boolean updateBookTracking(Integer userId, Integer bookId, LocalDate now);

    List<BookEntity> findBooksPerPage(int currentPage, int recordsPerPage);

    List<BookEntity> findBooksByStringParamPerPage(int currentPage, int recordsPerPage, String searchField);

    BookEntity saveBook(BookEntity bookEntity);

    void saveBookAuthors(Integer bookId, Integer authorId);

}
