package org.example.dataBaseCommunication;

import org.example.models.AccountModel;
import org.example.models.OrderModel;
import org.example.service.AccountsServices;
import org.example.service.OrdersServices;
import org.example.utils.Constants;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.example.appLogic.Orders.orderParser;
import static org.example.appLogic.Orders.ordersBatch;
import static org.example.properties.Config.BATCH_FILES_PARSED;
import static org.example.properties.Config.ORDERS_FOLDER_PATH;
import static org.example.utils.Constants.FIRST_COL;
import static org.example.validation.Validation.searchOrderNumber;

public class OrdersCommDb {
    public static void ordersCommunicator(List<OrderModel> orderList) throws SQLException {
        Connection connection=ConnectionIntializer.getConnection();
        transferOrdersListToMysql(connection,orderList);
        connection.close();
    }

    private static void transferOrdersRecordToMysql (Connection connection, OrderModel order) throws SQLException {
        String insertQuery = "INSERT INTO supplydemand.orders VALUES (?, ?, ?, ?)";
        boolean orderNumberExists = searchOrderNumber(order.getOrderNumber());
//        if(!orderNumberExists) {
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setInt(FIRST_COL, order.getOrderNumber());
                insertStatement.setString(Constants.SECOND_COL, order.getAccountId());
                insertStatement.setString(Constants.THIRD_COL, order.getItemId());
                insertStatement.setString(Constants.FOURTH_COL, order.getOrderQuantity());
                //insertStatement.executeUpdate();
                insertStatement.addBatch();
                insertStatement.executeBatch();
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        //}
    }

    private static void transferOrdersListToMysql (Connection connection, List < OrderModel > list) throws SQLException {

        for (int i = 0; i < list.size(); i++) {
            transferOrdersRecordToMysql(connection, list.get(i));
        }

    }
    private static void transferAllOrdersPassedOnBatchesToMysql (Connection connection, List <OrderModel> allOrders) throws SQLException
    {
        // residual=allAccounts.size()/BATCH_FILES_PARSED
        for(int i=0;i<allOrders.size();i++)
        {
            ordersBatch.add(allOrders.get(i));
            if(OrdersServices.isFullOrdersBatchList(ordersBatch,BATCH_FILES_PARSED))
            {
                transferOrdersBatchToMysql(connection,ordersBatch);
                ordersBatch.clear();
            }
        }
        transferOrdersBatchToMysql(connection,ordersBatch);
        ordersBatch.clear();
    }

    private static void transferOrdersBatchToMysql (Connection connection, @NotNull List < OrderModel > batchlist) throws SQLException {

        String insertQuery = "INSERT INTO supplydemand.accounts VALUES (?, ?, ?, ?, ?, ?)";
        //boolean mobileNumberExists = searchMobileNumber(acc.getMobileNumber());
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            //connection.setAutoCommit(false);
            for (OrderModel order : batchlist) {
                insertStatement.setInt(Constants.FIRST_COL, order.getOrderNumber());
                insertStatement.setString(Constants.SECOND_COL, order.getAccountId());
                insertStatement.setString(Constants.THIRD_COL, order.getItemId());
                insertStatement.setString(Constants.FOURTH_COL, order.getOrderQuantity());
                insertStatement.addBatch();
            }
            // then we will Execute the batch
            insertStatement.executeBatch();
            // Commit the changes
            //connection.commit();
            // we will Enable auto-commit
            //connection.setAutoCommit(true);
            insertStatement.close();
        }catch (SQLException e) {
            //e.printStackTrace();
        }
        //connection.close();
    }

}
