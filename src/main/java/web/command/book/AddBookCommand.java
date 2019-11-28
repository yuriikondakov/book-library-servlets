package web.command.book;

import domain.Author;
import domain.Book;
import org.apache.log4j.Logger;
import service.AuthorService;
import service.BookService;
import web.command.ActionType;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AddBookCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(AddBookCommand.class);
    private final BookService bookService;
    private final AuthorService authorService;

    public AddBookCommand(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) {
        LOGGER.debug("execute");
        if (actionType == ActionType.GET) {
            List <Author> authors = authorService.getAllAuthors();
            request.setAttribute("authorList", authors);
            return "addbook.jsp";
        } else {

            final Integer authorId = Integer.valueOf(request.getParameter("author"));
            final String name = request.getParameter("name");
            final String description =  request.getParameter("description");

            request.setAttribute("name", name);
            request.setAttribute("description", description);

            final List<Author> authors = (List<Author>) Collections.singletonList(authorService.findById(authorId));

            final Book book = Book.builder()
                    .withName(name)
                    .withAuthors(authors)
                    .withDescription(description)
                    .build();

            Book savedBook = bookService.addBook(book);
            LOGGER.debug(savedBook.toString());
            request.setAttribute("addBookSuccessful","Book added.");

            return "addbook.jsp";
        }
    }
}
