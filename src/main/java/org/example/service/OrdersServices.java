package org.example.service;

import org.example.models.OrderModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.example.properties.Config.*;

public class OrdersServices {
    public static boolean isFullOrdersBatchList(List<OrderModel> arrayList, int maxSize) {
        return arrayList.size() >= maxSize;
    }
    public static void deleteOrders() {
        String url = JDBCURL;
        String username = USER;
        String password = PASSWORD;
        String tableToDeleteFrom = "orders";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM " + tableToDeleteFrom)) {

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("All order records deleted successfully from " + tableToDeleteFrom);
            } else {
                System.out.println("No order records were deleted from " + tableToDeleteFrom);
            }

        } catch (SQLException e) {
            System.err.println("Error deleting order records: " + e.getMessage());
        }
    }
}
