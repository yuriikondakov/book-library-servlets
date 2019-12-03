package service.impl;

import dao.BookDao;
import dao.entity.AuthorEntity;
import dao.entity.BookEntity;
import domain.Author;
import domain.Book;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import service.mapper.BookMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {
    private static final BookEntity BOOK_ENTITY = initBookEntity();
    private static final Book BOOK = initBook();

    @Mock
    private BookDao bookDao;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    BookServiceImpl bookService;

    @After
    public void setAfter() {
        reset(bookDao);
    }

    @Test
    public void findByIdShouldReturnOptionalBook() {
        when(bookDao.findById(anyInt())).thenReturn(Optional.of(BOOK_ENTITY));
        when(bookMapper.mapBookEntityToBook(any(BookEntity.class))).thenReturn(BOOK);

       Book expected = bookService.findById(1);

        assertThat(BOOK, is(expected));
        verify(bookDao).findById(1);
    }

    @Test
    public void findAll() {
        when(bookDao.findAll()).thenReturn(Collections.singletonList(BOOK_ENTITY));
        when(bookMapper.mapBookEntityToBook(any(BookEntity.class))).thenReturn(BOOK);

        List<Book> expected = bookService.findAll();

        assertThat(Collections.singletonList(BOOK), is(expected));
        verify(bookDao).findAll();
    }

    @Test
    public void takeBook() {
        when(bookDao.saveBookTracking(anyInt(), anyInt(), any(LocalDate.class))).thenReturn(true);

        boolean expected = bookService.takeBook(1, 2);

        assertThat(true, is(expected));
    }

    @Test
    public void getNumberOfRows() {
        when(bookDao.findRowsCount()).thenReturn(30);

        int expected = bookService.getNumberOfRows();

        assertThat(30, is(expected));
    }

    @Test
    public void getBooksByUserId() {
        when(bookDao.findBooksByUserId(anyInt())).thenReturn(Collections.singletonList(BOOK_ENTITY));
        when(bookMapper.mapBookEntityToBook(any(BookEntity.class))).thenReturn(BOOK);

        List<Book> expected = bookService.getBooksByUserId(1);

        assertThat(Collections.singletonList(BOOK), is(expected));
        verify(bookDao).findBooksByUserId(1);
        verify(bookMapper).mapBookEntityToBook(any(BookEntity.class));
    }

    @Test
    public void returnBook() {
        when(bookDao.updateBookTracking(anyInt(), anyInt(), any(LocalDate.class))).thenReturn(true);

        boolean expected = bookService.returnBook(1, 2);

        assertThat(true, is(expected));
    }

    @Test
    public void getBooksPerPage() {
        when(bookDao.findBooksPerPage(anyInt(), anyInt())).thenReturn(Collections.singletonList(BOOK_ENTITY));
        when(bookMapper.mapBookEntityToBook(any(BookEntity.class))).thenReturn(BOOK);
        List<Book> expected = bookService.getBooksPerPage(1, 20);

        assertThat(Collections.singletonList(BOOK), is(expected));
    }

    @Test
    public void getBooksWithSearchPerPage() {
        when(bookDao.findBooksByStringParamPerPage(anyInt(), anyInt(), anyString()))
                .thenReturn(Collections.singletonList(BOOK_ENTITY));
        when(bookMapper.mapBookEntityToBook(any(BookEntity.class))).thenReturn(BOOK);

        List<Book> expected = bookService.getBooksWithSearchPerPage(1, 20, "searchField");

        assertThat(Collections.singletonList(BOOK), is(expected));
    }

    @Test
    public void addBook() {
        when(bookMapper.mapBookEntityToBook(any(BookEntity.class))).thenReturn(BOOK);
        when(bookMapper.mapBookToBookEntity(any(Book.class))).thenReturn(BOOK_ENTITY);
        when(bookDao.save(any(BookEntity.class))).thenReturn(BOOK_ENTITY);
        doNothing().when(bookDao).saveBookAuthors(anyInt(), anyInt());

        Book expected = bookService.addBook(BOOK);

        assertThat(BOOK, is(expected));
    }

    private static BookEntity initBookEntity() {

        AuthorEntity authorEntity = new AuthorEntity(1,"Bael", "Dung");

        return BookEntity.builder()
                .withId(1)
                .withName("BookName")
                .withDescription("Description")
                .withShelfId(1)
                .withAuthors(Collections.singletonList(authorEntity))
                .build();
    }

    private static Book initBook() {

        Author author = new Author(1,"Bael", "Dung");

        return Book.builder()
                .withId(1)
                .withName("BookName")
                .withDescription("Description")
                .withShelfId(1)
                .withAuthors(Collections.singletonList(author))
                .build();
    }
}