package org.example.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventoryServices {
    public static void deleteInventory() {
        String url = "jdbc:mysql://localhost:3306/supplydemand";
        String username = "root";
        String password = "Ym@01061905439";
        String tableToDeleteFrom = "inventory";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM " + tableToDeleteFrom)) {

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("All inventory records deleted successfully from " + tableToDeleteFrom);
            } else {
                System.out.println("No inventory records were deleted from " + tableToDeleteFrom);
            }

        } catch (SQLException e) {
            System.err.println("Error deleting inventory records: " + e.getMessage());
        }
    }
}
