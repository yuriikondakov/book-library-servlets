package web.command.book;

import domain.Author;
import domain.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import service.BookService;
import web.command.ActionType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookCommandTest {
    private static final Book BOOK = initBook();

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    //@Mock
    private ActionType actionType;
    @Mock
    private HttpSession session;
    @Mock
    private BookService bookService;

    @InjectMocks
    BookCommand bookCommand;

    @Test
    public void executeShouldReturnBookPage() {
        when(request.getParameter(any(String.class))).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(1);
        when(bookService.getBooksByUserId(anyInt())).thenReturn(Collections.emptyList());
        when(bookService.findById(anyInt())).thenReturn(BOOK);

        String actual = bookCommand.execute(request, response, actionType);
        String expected = "book.jsp";

        assertThat(actual, is(expected));
        verify(request).setAttribute(anyString(), any());
    }

    private static Book initBook() {
        Author author = new Author(1, "Bael", "Dung");
        return Book.builder()
                .withId(1)
                .withName("BookName")
                .withDescription("Description")
                .withShelfId(1)
                .withAuthors(Collections.singletonList(author))
                .build();
    }
}