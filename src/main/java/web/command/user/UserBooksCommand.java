package web.command.user;

import domain.Book;
import org.apache.log4j.Logger;
import service.BookService;
import web.command.ActionType;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserBooksCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(UserBooksCommand.class);
    private final BookService bookService;

    public UserBooksCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) {
        LOGGER.info("execute");

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        LOGGER.info("userId : "  + userId);

        List<Book> books = bookService.getBooksByUserId(userId);

        request.setAttribute("userBooksList", books);
        return "user_books.jsp";
    }
}
