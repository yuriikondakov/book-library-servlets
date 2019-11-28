package web.command.user;

import domain.User;
import org.apache.log4j.Logger;
import service.UserService;
import web.command.ActionType;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {
    private final UserService userService;
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) {

        if (actionType == ActionType.GET) {
            return "login.jsp";
        }

        String email = request.getParameter("email");
        request.setAttribute("email", email);
        String password = request.getParameter("password");
        LOGGER.debug("execute email and password are " + email + " and " + password);
        final User user = userService.login(email, password);
        if (user == null) {
            LOGGER.debug("user login not passed");
            request.setAttribute("loginWarningMessage", "Login or Password incorrect");
            return "login.jsp";
        }
        LOGGER.debug("user ok");
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("user_role", user.getRole());
        request.getSession().setAttribute("userId", user.getId());
        return "/front-controller?command=catalog";
    }
}
