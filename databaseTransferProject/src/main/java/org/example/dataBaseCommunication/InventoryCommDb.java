package org.example.dataBaseCommunication;

import org.example.models.InventoryModel;
import org.example.utils.Constants;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.example.appLogic.Inventory.inventoryParser;

public class InventoryCommDb {
        static String jdbcUrl = "jdbc:mysql://localhost:3306/supplydemand";
        static String user = "root";
        static String password = "Ym@01061905439";
        static String folderPath = "D:\\task1 24-1-2024\\Inventory Folder";
        public static void inventoryConnector(){
            try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password)) {
                List<InventoryModel> invList;
                try {
                    invList = inventoryParser(folderPath);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                transferInvenotoryListToMysql(connection,invList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private static void transferInventoryRecordToMysql (Connection connection, InventoryModel inv) {
            String insertQuery = "INSERT INTO supplydemand.inventory VALUES (?, ?, ?)";
            //boolean mobileNumberExists = searchMobileNumber(acc.getMobileNumber());
            //if (!mobileNumberExists) {
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setString(Constants.FIRST_COL, inv.getItemName());
                    insertStatement.setDouble(Constants.SECOND_COL, inv.getItemPrice());
                    insertStatement.setInt(Constants.THIRD_COL, inv.getInventoryQuantity());
                    insertStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
           // }
        }
        private static void transferInvenotoryListToMysql (Connection connection, List < InventoryModel > list){

            for (InventoryModel inventoryModel : list) {
                transferInventoryRecordToMysql(connection, inventoryModel);
            }

        }

    }

