package org.example.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrdersServices {
    public static void deleteOrders() {
        String url = "jdbc:mysql://localhost:3306/supplydemand";
        String username = "root";
        String password = "Ym@01061905439";
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
