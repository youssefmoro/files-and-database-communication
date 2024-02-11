package org.example.periodicity.accountsTasks;

import org.example.dataBaseCommunication.AccountsCommDb;
import org.example.models.AccountModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

//import static org.example.periodicity.accountsTasks.AccountsConnectionIntializerTask.dataSource;
import static org.example.periodicity.accountsTasks.AccountsConnectionIntializerTask.getConnection;

public class InsertBatchIntoDataBase {
    public static void insertBatchIntoDataBase(List<AccountModel> batch) throws SQLException {
        Connection connection=getConnection();
        try {
            AccountsCommDb.transferAccountsBatchToMysql(connection,batch);
            // Shut down the pool when finished
            //dataSource.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close the connection to return it to the pool
            connection.close();
        }
    }
}
