package org.example.periodicity.inventoryTasks;

import org.example.dataBaseCommunication.InventoryCommDb;
import org.example.dataBaseCommunication.OrdersCommDb;
import org.example.models.InventoryModel;
import org.example.models.OrderModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.example.periodicity.commonTasks.ConnectionIntializerTask.getConnection;

public class Insert_InventoryBatchIntoDB {
    public static void insertInventoryBatchIntoDataBase(List<InventoryModel> batch) throws SQLException {
        Connection connection=getConnection();
        try {
            InventoryCommDb.transferInventoryBatchToMysql(connection,batch);
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
