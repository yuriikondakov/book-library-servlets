package service.impl;

import dao.BookDao;
import dao.entity.BookEntity;
import domain.Book;
import org.apache.log4j.Logger;
import service.BookService;
import service.mapper.BookMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = Logger.getLogger(BookServiceImpl.class);
    private final BookDao bookDao;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookDao bookDao, BookMapper bookMapper) {
        this.bookDao = bookDao;
        this.bookMapper = bookMapper;
    }

    @Override
    public Book findById(Integer id) {
        return bookDao.findById(id).map(bookMapper::mapBookEntityToBook).orElse(null);
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll().stream().map(bookMapper::mapBookEntityToBook).collect(Collectors.toList());
    }

    @Override
    public boolean takeBook(Integer userId, Integer bookId) {
        return bookDao.saveBookTracking(userId, bookId, LocalDate.now());
    }

    @Override
    public int getNumberOfRows() {
        return bookDao.findRowsCount();
    }

    @Override
    public List<Book> getBooksByUserId(Integer userId) {
        return bookDao.findBooksByUserId(userId)
                .stream().map(bookMapper::mapBookEntityToBook).collect(Collectors.toList());
    }

    @Override
    public boolean returnBook(Integer userId, Integer bookId) {
        return bookDao.updateBookTracking(userId, bookId, LocalDate.now());
    }

    @Override
    public List<Book> getBooksPerPage(int currentPage, int recordsPerPage) {
        return bookDao.findBooksPerPage(currentPage, recordsPerPage)
                .stream().map(bookMapper::mapBookEntityToBook).collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooksWithSearchPerPage(int currentPage, int recordsPerPage, String searchField) {
        return bookDao.findBooksByStringParamPerPage(currentPage, recordsPerPage, searchField)
                .stream().map(bookMapper::mapBookEntityToBook).collect(Collectors.toList());
    }

    @Override
    public Book addBook(Book book) {
        BookEntity savedBookEntity = bookDao.saveBook(bookMapper.mapBookToBookEntity(book));
        bookDao.saveBookAuthors(savedBookEntity.getId(), book.getAuthors().stream().findFirst().get().getId());
        return bookMapper.mapBookEntityToBook(savedBookEntity);
    }
}
