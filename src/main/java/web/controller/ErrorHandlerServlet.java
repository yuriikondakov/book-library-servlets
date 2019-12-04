package web.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import static javax.servlet.RequestDispatcher.*;

public class ErrorHandlerServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html; charset=utf-8");
        try (PrintWriter writer = resp.getWriter()) {
            writer.write("<html><head>" +
                    "<title>Error description</title>" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/bootstrap.min.css\">" +
                    "</head><body>");
            writer.write("<div class=\"container\">\n" +
                    "    <div class=\"row\">\n" +
                    "        <div class=\"col-3\"></div>\n" +
                    "        <div class=\"col-6 mt-5\">\n" +
                    "            <div class=\"alert alert-danger\" role=\"alert\">\n" +
                    "                Oops, something went wrong\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "        <div class=\"col-3\"></div>\n" +
                    "    </div>\n" +
                    "</div>");
            writer.write("</html></body>");
        }
    }
}
