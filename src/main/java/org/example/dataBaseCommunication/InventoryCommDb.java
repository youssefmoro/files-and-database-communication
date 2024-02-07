package org.example.dataBaseCommunication;

import org.example.models.InventoryModel;
import org.example.utils.Constants;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.example.appLogic.Inventory.inventoryParser;
import static org.example.properties.Config.*;

public class InventoryCommDb {
        public static void inventoryCommunicator() throws SQLException {
            Connection connection=ConnectionIntializer.getConnection();
                List<InventoryModel> invList;
                try {
                    invList = inventoryParser(INVENTORY_FOLDER_PATH);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                transferInvenotoryListToMysql(connection,invList);
                connection.close();

        }

        private static void transferInventoryRecordToMysql (Connection connection, InventoryModel inv) {
            String insertQuery = "INSERT INTO supplydemand.inventory VALUES (?, ?, ?)";

                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setString(Constants.FIRST_COL, inv.getItemName());
                    insertStatement.setDouble(Constants.SECOND_COL, inv.getItemPrice());
                    insertStatement.setInt(Constants.THIRD_COL, inv.getInventoryQuantity());
                    //insertStatement.executeUpdate();
                    insertStatement.addBatch();
                    insertStatement.executeBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        private static void transferInvenotoryListToMysql (Connection connection, List < InventoryModel > list){

            for (InventoryModel inventoryModel : list) {
                transferInventoryRecordToMysql(connection, inventoryModel);
            }

        }

    }

