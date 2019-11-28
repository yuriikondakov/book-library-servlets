package web.controller;

import context.ApplicationContextInjector;
import org.apache.log4j.Logger;
import web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import static web.command.ActionType.GET;
import static web.command.ActionType.POST;

public class FrontController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(FrontController.class);

    private final Map<String, Command> commandNameToCommand;
    private final Command defaultCommand;

    public FrontController() {
        final ApplicationContextInjector injector = ApplicationContextInjector.getInstance();
        this.commandNameToCommand = injector.getUserCommandNameToCommand();
        this.defaultCommand = (request, response, actionType) -> "index.jsp";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String commandName = req.getParameter("command");
        LOGGER.info("doPost - commandName:" + commandName);
        Enumeration<String> params = req.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = params.nextElement();
            System.out.println("Parameter Name - "+paramName+", Value - "+req.getParameter(paramName));
        }
        final String page = commandNameToCommand.getOrDefault(commandName, defaultCommand).execute(req, resp, POST);
        LOGGER.info("doPost - page:" + page);
        req.getRequestDispatcher(page).forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter("command");
        LOGGER.info("doGet - commandName:" + commandName);
        final String page = commandNameToCommand.getOrDefault(commandName, defaultCommand).execute(req, resp, GET);
        LOGGER.info("doGet - page:" + page);
        req.getRequestDispatcher(page).forward(req, resp);
    }
}
