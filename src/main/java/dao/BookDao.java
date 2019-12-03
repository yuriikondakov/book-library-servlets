package dao;

import dao.entity.BookEntity;

import java.time.LocalDate;
import java.util.List;

public interface BookDao extends CrudDao<BookEntity, Integer> {

    int findRowsCount();

    boolean saveBookTracking(Integer userId, Integer bookId, LocalDate now);

    List<BookEntity> findBooksByUserId(Integer userId);

    boolean updateBookTracking(Integer userId, Integer bookId, LocalDate now);

    List<BookEntity> findBooksPerPage(int currentPage, int recordsPerPage);

    List<BookEntity> findBooksByStringParamPerPage(int currentPage, int recordsPerPage, String searchField);

    BookEntity save(BookEntity bookEntity);

    void saveBookAuthors(Integer bookId, Integer authorId);

}
