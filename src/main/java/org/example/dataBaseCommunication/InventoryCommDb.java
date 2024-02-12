package org.example.dataBaseCommunication;

import jdk.nashorn.internal.runtime.ECMAException;
import org.example.models.InventoryModel;
import org.example.models.OrderModel;
import org.example.periodicity.commonTasks.ConnectionIntializerTask;
import org.example.service.InventoryServices;
import org.example.service.OrdersServices;
import org.example.utils.Constants;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.example.appLogic.Inventory.inventoryBatch;
import static org.example.appLogic.Inventory.inventoryParser;
import static org.example.appLogic.Orders.ordersBatch;
import static org.example.properties.Config.*;

public class InventoryCommDb {

    public static void inventoryCommunicator(List<InventoryModel> inventoryList) throws SQLException {
        try (Connection connection = ConnectionIntializer.getConnection()) {
            transferAllInventoryPassedOnBatchesToMysql(connection, inventoryList);
        }catch (Exception e)
        {
            e.getStackTrace();
        }
    }

    private static void transferInventoryRecordToMysql(Connection connection, InventoryModel inv) {
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

    private static void transferInvenotoryListToMysql(Connection connection, List<InventoryModel> list) {

        for (InventoryModel inventoryModel : list) {
            transferInventoryRecordToMysql(connection, inventoryModel);
        }

    }

    private static void transferAllInventoryPassedOnBatchesToMysql(Connection connection, List<InventoryModel> allInventory) throws SQLException {
        for (int i = 0; i < allInventory.size(); i++) {
            inventoryBatch.add(allInventory.get(i));
            if (InventoryServices.isFullInventoryBatchList(inventoryBatch, BATCH_FILES_PARSED)) {
                transferInventoryBatchToMysql(connection, inventoryBatch);
                ordersBatch.clear();
            }
        }
        transferInventoryBatchToMysql(connection, inventoryBatch);
        inventoryBatch.clear();
    }

    public static void transferInventoryBatchToMysql(Connection connection, @NotNull List<InventoryModel> batchlist) throws SQLException {

        String insertQuery = "INSERT INTO supplydemand.inventory VALUES (?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            //connection.setAutoCommit(false);
            for (InventoryModel inventory : batchlist) {
                insertStatement.setString(Constants.FIRST_COL, inventory.getItemName());
                insertStatement.setDouble(Constants.SECOND_COL,inventory.getItemPrice() );
                insertStatement.setInt(Constants.THIRD_COL, inventory.getInventoryQuantity());
                insertStatement.addBatch();
            }
            // then we will Execute the batch
            insertStatement.executeBatch();

        } catch (SQLException e) {
            //e.printStackTrace();
        }
        //connection.close();

    }
}

