package web.filter;

import domain.Role;
import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class SecurityFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(SecurityFilter.class);
    private final Map<Role, List<String>> accessMap = new HashMap<>();
    private List<String> commons;

    @Override
    public void init(FilterConfig config) throws ServletException {
        LOGGER.debug("Filter initialization starts");
        accessMap.put(Role.ADMIN, asList(config.getInitParameter("admin").split(" ")));
        accessMap.put(Role.USER, asList(config.getInitParameter("user").split(" ")));
        LOGGER.trace("Access map ==> " + accessMap);

        commons = asList(config.getInitParameter("common").split(" "));
        LOGGER.trace("Common commands ==> " + commons);

        LOGGER.debug("Filter initialization finished");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
       final HttpServletRequest request  = (HttpServletRequest) servletRequest;
       final HttpServletResponse response  = (HttpServletResponse) servletResponse;

        LOGGER.debug("Filter starts");

        if (accessAllowed(request)) {
            LOGGER.debug("Filter finished");
            filterChain.doFilter(request, response);
        } else {
            request.setAttribute("errorMessage", "security filter not passed");
            LOGGER.info("security filter not passed");

            request.getRequestDispatcher("not_allowed.jsp").forward(request, response);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");
        LOGGER.trace("Command name ==> " + commandName);
        if (commandName == null || commandName.isEmpty()) {
            return false;
        }

        if (commons.contains(commandName)) {
            return true;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }

        Role userRole = (Role) session.getAttribute("user_role");
        if (userRole == null) {
            return false;
        }

        return accessMap.get(userRole).contains(commandName);
    }
}
