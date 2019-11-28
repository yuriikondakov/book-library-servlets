package web.command.book;

import org.apache.log4j.Logger;
import service.BookService;
import web.command.ActionType;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReturnBookCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(ReturnBookCommand.class);
    private final BookService bookService;

    public ReturnBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) {
        LOGGER.info("execute");
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        LOGGER.info("bookId : " + bookId);
        int userId = (Integer) request.getSession().getAttribute("userId");
        LOGGER.info("userId : "  + userId);

        if (bookService.returnBook(userId, bookId)) {
            return "front-controller?command=my_books";
        } else {
            LOGGER.warn("can`t return book");
            return "book.jsp";
        }
    }
}
