package ru.akirakozov.sd.refactoring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseRequest {
    private static final String CREATE_DATABASE = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";
    private static final String DB_ERROR = "Database access error";

    private final String databaseName;

    public DatabaseRequest() {
        this.databaseName = "jdbc:sqlite:test.db";
    }

    private void executeUpdate(String sqlRequest) {
        try (Connection c = DriverManager.getConnection(databaseName)) {
            try (Statement statement = c.createStatement()) {
                statement.executeUpdate(sqlRequest);
            }
        } catch (SQLException ignored) {
            throw new RuntimeException(DB_ERROR);
        }
    }

    public void createDatabase() {
        executeUpdate(CREATE_DATABASE);
    }
}
