package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DatabaseRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractDatabaseServlet {
    public QueryServlet(DatabaseRequest databaseRequest) {
        super(databaseRequest);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        switch (command) {
            case "max":
                execute(response, databaseRequest::queryMaxProduct, databaseRequest::toHtml, "<h1>Product with max price: </h1>");
                break;
            case "min":
                execute(response, databaseRequest::queryMinProduct, databaseRequest::toHtml, "<h1>Product with min price: </h1>");
                break;
            case "sum":
                execute(response, databaseRequest::querySumProduct, databaseRequest::toHtml, "Summary price: ");
                break;
            case "count":
                execute(response, databaseRequest::queryCountProduct, databaseRequest::toHtml, "Number of products: ");
                break;
            default:
                response.getWriter().println("Unknown command: " + command);
                break;
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
