package web.command.user;

import web.command.ActionType;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) {
        HttpSession session=request.getSession();
        String lang = (String) session.getAttribute("lang");
        session.invalidate();
        HttpSession newSession=request.getSession();
        newSession.setAttribute("lang", lang);
        return "/front-controller?command=library";
    }
}
