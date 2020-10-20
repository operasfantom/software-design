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
        responseHandler.reset(response);

        execute(databaseRequest::queryGetProduct, databaseRequest::toHtml, null);

        responseHandler.flush(HttpServletResponse.SC_OK, true);
    }
}
