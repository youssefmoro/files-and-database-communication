package org.example.dataBaseCommunication;

import org.example.models.OrderModel;
import org.example.utils.Constants;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.example.appLogic.Orders.orderParser;

public class OrdersCommDb {
    public static void ordersConnector(){
        String jdbcUrl = "jdbc:mysql://localhost:3306/supplydemand";
        String user = "root";
        String password = "Ym@01061905439";
        String folderPath = "D:\\task1 24-1-2024\\Orders Folder";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password)) {
            List<OrderModel> orderList;
            try {
                orderList = orderParser(folderPath);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            transferOrdersListToMysql(connection,orderList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void transferOrdersRecordToMysql (Connection connection, OrderModel order){
        String insertQuery = "INSERT INTO supplydemand.orders VALUES (?, ?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setLong(Constants.FIRST_COL,order.getOrderNumber());
            insertStatement.setLong(Constants.SECOND_COL,order.getAccountId());
            insertStatement.setLong(Constants.THIRD_COL,order.getItemId());
            insertStatement.setLong(Constants.FOURTH_COL,order.getOrderQuantity());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void transferOrdersListToMysql (Connection connection, List < OrderModel > list) {

        for (int i = 0; i < list.size(); i++) {
            transferOrdersRecordToMysql(connection, list.get(i));
        }

    }
}
