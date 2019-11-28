package web.command.user;

import domain.User;
import org.apache.log4j.Logger;
import service.UserService;
import web.command.ActionType;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class LocaleCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(LocaleCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) {
        LOGGER.debug("execute");
        if (request.getParameter("locale") != null) {
            request.getSession().setAttribute("lang", request.getParameter("locale"));
            LOGGER.info("locale is NOT null, setting locale");
        }
        return "index.jsp";
    }
}
