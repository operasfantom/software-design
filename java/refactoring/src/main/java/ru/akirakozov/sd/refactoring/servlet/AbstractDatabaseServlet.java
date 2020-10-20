package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DatabaseRequest;

import javax.servlet.http.HttpServlet;
import java.util.Objects;

public abstract class AbstractDatabaseServlet extends HttpServlet {
    final DatabaseRequest databaseRequest;

    AbstractDatabaseServlet(DatabaseRequest databaseRequest) {
        this.databaseRequest = Objects.requireNonNull(databaseRequest, "databaseRequest");
    }
}
