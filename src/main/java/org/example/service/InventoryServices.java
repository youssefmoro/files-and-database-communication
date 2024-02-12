package org.example.service;

import org.example.models.InventoryModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.example.properties.Config.*;

public class InventoryServices {
    public static boolean isFullInventoryBatchList(List<InventoryModel> arrayList, int maxSize) {
        return arrayList.size() >= maxSize;
    }
    public static void deleteInventory() {
        String url = JDBCURL;
        String username = USER;
        String password = PASSWORD;
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
