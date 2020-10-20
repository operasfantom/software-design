package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DatabaseRequest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractDatabaseServlet extends HttpServlet {
    final DatabaseRequest databaseRequest;

    AbstractDatabaseServlet(DatabaseRequest databaseRequest) {
        this.databaseRequest = Objects.requireNonNull(databaseRequest, "databaseRequest");
    }

    static <T> void execute(HttpServletResponse response, Supplier<T> valueExtractor, Function<T, String> toStringConverter, String htmlHeader) throws IOException {
        response.getWriter().println("<html><body>");
        if (htmlHeader != null) {
            response.getWriter().println(htmlHeader);
        }

        var value = valueExtractor.get();
        var string = toStringConverter.apply(value);

        response.getWriter().println(string);

        response.getWriter().println("</body></html>");
    }
}
