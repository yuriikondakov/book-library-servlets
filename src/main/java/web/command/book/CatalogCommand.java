package web.command.book;

import domain.Book;
import org.apache.log4j.Logger;
import service.BookService;
import web.command.ActionType;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CatalogCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CatalogCommand.class);
    private final BookService bookService;

    public CatalogCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) {
        LOGGER.info("execute");
        request.setAttribute("currentPage", getRequestParameter(request, "currentPage"));
        request.setAttribute("recordsPerPage", getRequestParameter(request, "recordsPerPage"));

        int currentPage = Integer.parseInt((String) request.getAttribute("currentPage"));
        if (currentPage <= 0) {
            currentPage = 1;
        }
        int recordsPerPage = Integer.parseInt((String) request.getAttribute("recordsPerPage"));

        List<Book> books;
        int rows;
        String searchField = request.getParameter("searchField");
        if (searchField == null) {
            LOGGER.debug("searchField is empty");
            books = bookService.getBooksPerPage(currentPage, recordsPerPage);
            rows = bookService.getNumberOfRows();
        } else {
            LOGGER.debug("search field is [" + searchField + "]");
            books = bookService.getBooksWithSearchPerPage(currentPage, recordsPerPage, searchField);
            request.setAttribute("searchField", searchField);
            rows = bookService.getBooksWithSearchPerPage(1, 100, searchField).size();
            LOGGER.debug("search count rows is [" + rows + "]");
        }

        request.setAttribute("bookList", books);

        int nOfPages = rows / recordsPerPage;
        if (rows % recordsPerPage > 0) {
            nOfPages++;
        }
        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);

        return "catalog.jsp";
    }

    private String getRequestParameter(HttpServletRequest request, String name) {
        String param = request.getParameter(name);
        return (param == null) ? getInitParameter(name) : param;
    }

    private String getInitParameter(String name) {
        if ("currentPage".equals(name)) {
            return "1";
        }
        if ("recordsPerPage".equals(name)) {
            return "10";
        }
        return "";
    }
}
