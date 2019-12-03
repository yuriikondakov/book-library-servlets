package web.command.user;

import domain.Role;
import domain.User;
import org.apache.log4j.Logger;
import service.UserService;
import web.command.ActionType;
import web.command.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class RegisterCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(RegisterCommand.class);
    private final UserService userService;

    public RegisterCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) {

        if (actionType == ActionType.GET) {
            return "registration.jsp";
        }

        final String name = request.getParameter("name");
        final String email =  request.getParameter("email");

        request.setAttribute("name", name);
        request.setAttribute("email", email);

        final String password1 =  request.getParameter("password1");
        final String password2 =  request.getParameter("password2");
        final String phone =  request.getParameter("phone");
        LOGGER.debug("name-email-pass1-pass2" + name + ":" + email + ":" + password1 + ":" + password2);

        if (!userService.isValidEmail(email)) {
            LOGGER.debug("not valid email");
            request.setAttribute("registrationWarningMessage","not valid email");
            return "registration.jsp";
        }

        if (userService.isExistEmail(email)) {
            LOGGER.debug("already exist email");
            request.setAttribute("registrationWarningMessage","already exist email");
            return "registration.jsp";
        }

        if (!userService.isValidPassword(password1)) {
            LOGGER.debug("not valid password");
            request.setAttribute("registrationWarningMessage","not valid password, must be at least 5 symbols");
            return "registration.jsp";
        }

        if (!Objects.equals(password1, password2)) {
            LOGGER.info("passwords not equals");
            request.setAttribute("registrationWarningMessage","password must be equals");
            return "registration.jsp";
        }

        final User user = User.builder()
                .withEmail(email)
                .withName(name)
                .withPassword(password1)
                .withPhoneNumber(phone)
                .withRole(Role.USER)
                .build();

        User registeredUser = userService.register(user);
        LOGGER.debug(registeredUser.toString());
        request.setAttribute("registrationMessage","Successful registration!");

        return "login.jsp";
    }
}
