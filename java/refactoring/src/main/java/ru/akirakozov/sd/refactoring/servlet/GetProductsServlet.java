package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DatabaseRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractDatabaseServlet {

    public GetProductsServlet(DatabaseRequest databaseRequest) {
        super(databaseRequest);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        execute(response, databaseRequest::queryGetProduct, databaseRequest::toHtml, null);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
