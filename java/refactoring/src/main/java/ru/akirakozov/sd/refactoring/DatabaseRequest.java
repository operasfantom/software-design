package ru.akirakozov.sd.refactoring;

import javax.validation.constraints.NotNull;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DatabaseRequest {
    public static final String MAX_QUERY = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
    public static final String MIN_QUERY = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
    public static final String SUM_QUERY = "SELECT SUM(price) FROM PRODUCT";
    public static final String COUNT_QUERY = "SELECT COUNT(*) FROM PRODUCT";
    public static final String GET_QUERY = "SELECT * FROM PRODUCT";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS PRODUCT";
    private static final String CREATE_DATABASE = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";

    private static final String DB_ERROR = "Database access error";

    private final String databaseName;

    public DatabaseRequest() {
        this.databaseName = "jdbc:sqlite:test.db";
    }

    public void createDatabase() {
        update(CREATE_DATABASE);
    }

    public void dropDatabase() {
        update(DROP_TABLE);
    }

    //region convert value to html
    public String toHtml(List<Product> list) {
        return list.stream()
                .map(product -> product.getName() + "\t" + product.getPrice() + "</br>")
                .collect(Collectors.joining());
    }

    public String toHtml(long l) {
        return Long.toString(l);
    }
    //endregion

    //region query or update
    private <T> T query(@NotNull String sqlRequest, @NotNull Function<ResultSet, T> extractor) {
        try (Connection c = DriverManager.getConnection(databaseName)) {
            try (Statement statement = c.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sqlRequest)) {
                    return extractor.apply(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(DB_ERROR);
        }
    }

    private void update(String sqlRequest) {
        try (Connection c = DriverManager.getConnection(databaseName)) {
            try (Statement statement = c.createStatement()) {
                statement.executeUpdate(sqlRequest);
            }
        } catch (SQLException e) {
            throw new RuntimeException(DB_ERROR);
        }
    }
    //endregion

    //region get values from ResultSet
    private List<Product> getList(ResultSet resultSet) {
        try {
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                var product = Product.fromResultSet(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long getLong(ResultSet resultSet) {
        try {
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion

    //region get value from query request
    public List<Product> queryMaxProduct() {
        return query(MAX_QUERY, this::getList);
    }

    public List<Product> queryMinProduct() {
        return query(MIN_QUERY, this::getList);
    }

    public long querySumProduct() {
        return query(SUM_QUERY, this::getLong);
    }

    public long queryCountProduct() {
        return query(COUNT_QUERY, this::getLong);
    }

    public List<Product> queryGetProduct() {
        return query(GET_QUERY, this::getList);
    }

    public void updateAddProduct(Product product) {
        var sqlRequest = "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")";
        update(sqlRequest);
    }
    //endregion
}
