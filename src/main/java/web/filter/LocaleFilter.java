package web.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LocaleFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(LocaleFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getSession().getAttribute("lang") == null) {
            req.getSession().setAttribute("lang", "uk");
            LOGGER.info("lang is null, setting uk");
        }

/*        if (req.getParameter("locale") != null) {
            req.getSession().setAttribute("lang", req.getParameter("locale"));
            LOGGER.info("locale is NOT null, setting locale");
        }*/
        chain.doFilter(request, response);
    }
}
