package web.command.book;

import domain.Author;
import org.apache.log4j.Logger;
import service.AuthorService;
import web.command.ActionType;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddAuthorCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(AddAuthorCommand.class);
    private final AuthorService authorService;

    public AddAuthorCommand(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) {
        LOGGER.debug("execute");
        if (actionType == ActionType.GET) {
            return "add_author.jsp";
        } else {

            final String firstName = request.getParameter("firstName");
            final String lastName = request.getParameter("lastName");

            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);

            Author author = new Author(firstName, lastName);
            Author savedAuthor = authorService.save(author);
            request.setAttribute("addAuthorSuccessful", "Author added.");

            return "add_author.jsp";
        }
    }
}
