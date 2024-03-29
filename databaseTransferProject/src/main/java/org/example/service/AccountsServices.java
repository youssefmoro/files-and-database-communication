package org.example.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountsServices {
    public static void deleteAccounts() {
        String url = "jdbc:mysql://localhost:3306/supplydemand";
        String username = "root";
        String password = "Ym@01061905439";
        String tableToDeleteFrom = "accounts";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM " + tableToDeleteFrom)) {

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("All records deleted successfully from " + tableToDeleteFrom);
            } else {
                System.out.println("No records were deleted from " + tableToDeleteFrom);
            }

        } catch (SQLException e) {
            System.err.println("Error deleting records: " + e.getMessage());
        }
    }
    public static void deleteRecordFromAccounts(long accountId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/supplydemand", "root", "Ym@01061905439");
             PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE accountId = ?")) {

            statement.setLong(1, accountId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Row deleted successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
