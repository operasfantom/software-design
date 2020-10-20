package ru.akirakozov.sd.refactoring;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    private final String name;
    private final long price;

    public Product(String name, long price) {

        this.name = name;
        this.price = price;
    }

    public static Product fromResultSet(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        int price = resultSet.getInt("price");
        return new Product(name, price);
    }

    public static Product fromRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        return new Product(name, price);
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }
}
