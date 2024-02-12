package org.example.periodicity.ordersTasks;

import org.example.dataBaseCommunication.OrdersCommDb;
import org.example.models.OrderModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.example.periodicity.commonTasks.ConnectionIntializerTask.getConnection;

public class InsertOrdersBatchIntoDB {
    public static void insertBatchIntoDataBase(List<OrderModel> batch) throws SQLException {
        Connection connection=getConnection();
        try {
           OrdersCommDb.transferOrdersBatchToMysql(connection,batch);
            // Shut down the pool when finished
            //dataSource.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            // Close the connection to return it to the pool
            connection.close();
        }
    }
}
