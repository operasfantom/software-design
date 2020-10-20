package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DatabaseRequest;
import ru.akirakozov.sd.refactoring.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractDatabaseServlet {

    public AddProductServlet(DatabaseRequest databaseRequest) {
        super(databaseRequest);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var product = Product.fromRequest(request);

        databaseRequest.updateAddProduct(product);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().println("OK");
    }
}
