package web.command.book;

import domain.Book;
import org.apache.log4j.Logger;
import service.BookService;
import web.command.ActionType;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(BookCommand.class);
    private final BookService bookService;

    public BookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) {

        LOGGER.debug("execute");
        int bookId = Integer.parseInt(request.getParameter("id"));
        int userId = (Integer) request.getSession().getAttribute("userId");
        if (bookId <= 0) {
            return "error-404.jsp";
        }

        if (bookService.getBooksByUserId(userId).stream().anyMatch(book -> book.getId().equals(bookId))) {
            request.setAttribute("bookIsAlreadyTaken", true);
        }

        Book book = bookService.findById(bookId);
        request.setAttribute("book", book);

        return "book.jsp";
    }

}
