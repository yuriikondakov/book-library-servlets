package web.command.book;

import org.apache.log4j.Logger;
import service.BookService;
import web.command.ActionType;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TakeBookCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(TakeBookCommand.class);
    private final BookService bookService;

    public TakeBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) {

        Integer bookId = Integer.parseInt(request.getParameter("bookId"));
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        if (bookService.takeBook(userId, bookId)) {
            request.setAttribute("takeBookSuccessful","Successful");
            return "front-controller?command=book&id="+bookId;
        } else {
            LOGGER.warn("can`t take book");
            return "book.jsp";
        }
    }
}
