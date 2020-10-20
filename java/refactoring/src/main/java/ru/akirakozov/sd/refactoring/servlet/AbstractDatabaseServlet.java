package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DatabaseRequest;
import ru.akirakozov.sd.refactoring.ResponseHandler;

import javax.servlet.http.HttpServlet;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractDatabaseServlet extends HttpServlet {
    protected final ResponseHandler responseHandler;
    final DatabaseRequest databaseRequest;

    AbstractDatabaseServlet(DatabaseRequest databaseRequest) {
        this.databaseRequest = Objects.requireNonNull(databaseRequest, "databaseRequest");
        this.responseHandler = new ResponseHandler();
    }

    <T> void execute(Supplier<T> valueExtractor, Function<T, String> toStringConverter, String htmlHeader) {
        if (htmlHeader != null) {
            responseHandler.writeln(htmlHeader);
        }
        var value = valueExtractor.get();
        var string = toStringConverter.apply(value);
        responseHandler.writeln(string);
    }
}
