package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DatabaseRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_OK;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractDatabaseServlet {
    public QueryServlet(DatabaseRequest databaseRequest) {
        super(databaseRequest);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        responseHandler.reset(response);

        String command = request.getParameter("command");
        switch (command) {
            case "max":
                execute(databaseRequest::queryMaxProduct, databaseRequest::toHtml, "<h1>Product with max price: </h1>");
                break;
            case "min":
                execute(databaseRequest::queryMinProduct, databaseRequest::toHtml, "<h1>Product with min price: </h1>");
                break;
            case "sum":
                execute(databaseRequest::querySumProduct, databaseRequest::toHtml, "Summary price: ");
                break;
            case "count":
                execute(databaseRequest::queryCountProduct, databaseRequest::toHtml, "Number of products: ");
                break;
            default:
                response.getWriter().println("Unknown command: " + command);
                break;
        }

        responseHandler.flush(SC_OK, true);
    }

}
