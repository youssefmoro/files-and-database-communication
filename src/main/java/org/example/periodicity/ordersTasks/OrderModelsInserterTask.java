package org.example.periodicity.ordersTasks;

import org.example.dataBaseCommunication.OrdersCommDb;
import org.example.models.OrderModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OrderModelsInserterTask {
        public static void ordersModelInserter(List<OrderModel> orderList) throws SQLException, IOException {
            OrdersCommDb.ordersCommunicator(orderList);
    }
}
