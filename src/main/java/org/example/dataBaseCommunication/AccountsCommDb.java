package org.example.dataBaseCommunication;

import org.example.models.AccountModel;
import org.example.utils.Constants;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

import static org.example.properties.Config.BATCH_FILES_PARSED;
import static org.example.validation.Validation.searchMobileNumber;
import org.example.service.AccountsServices;
import org.jetbrains.annotations.NotNull;

public class AccountsCommDb {
    public  static List<AccountModel> accountsBatch = new ArrayList<>(BATCH_FILES_PARSED);

    //static int batch_count=Constants.LOW;
    public static void accountsCommunicator(List<AccountModel> accList) throws SQLException, IOException {
    Connection connection=ConnectionIntializer.getConnection();
    //transferAccountsListToMysql(connection, accList);
        transferAllAccountsPassedOnBatchesToMysql(connection,accList);
    connection.close();
    }
    private static void transferAccountRecordToMysql (Connection connection, AccountModel acc) throws SQLException {
        String insertQuery = "INSERT INTO supplydemand.accounts VALUES (?, ?, ?, ?, ?, ?)";
        boolean mobileNumberExists = searchMobileNumber(acc.getMobileNumber());
        if (!mobileNumberExists) {
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setInt(Constants.FIRST_COL, acc.getAccountId());
                    insertStatement.setString(Constants.SECOND_COL, acc.getMobileNumber());
                    insertStatement.setString(Constants.THIRD_COL, acc.getMobileNumber());
                    insertStatement.setString(Constants.FOURTH_COL, acc.getEmail());
                    insertStatement.setString(Constants.FIFITH_COL, acc.getAddress());
                    insertStatement.setString(Constants.SIXTH_COL, acc.getCardNumber());
                    //insertStatement.executeUpdate();
                    insertStatement.addBatch();
                    insertStatement.executeBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
            private static void transferAccountsListToMysql (Connection connection, List < AccountModel > list) throws SQLException {

                for (AccountModel accountModel : list) {
                    transferAccountRecordToMysql(connection, accountModel);
                }

            }
    private static void transferAllAccountsPassedOnBatchesToMysql (Connection connection, List < AccountModel > allAccounts) throws SQLException
    {
        // residual=allAccounts.size()/BATCH_FILES_PARSED
        for(int i=0;i<allAccounts.size();i++)
        {
            accountsBatch.add(allAccounts.get(i));
            if(AccountsServices.isFullAccountsBatchList(accountsBatch,BATCH_FILES_PARSED))
            {
                transferAccountsBatchToMysql(connection,accountsBatch);
                accountsBatch.clear();
            }
        }
        transferAccountsBatchToMysql(connection,accountsBatch);
        accountsBatch.clear();
    }

    private static void transferAccountsBatchToMysql (Connection connection, @NotNull List < AccountModel > batchlist) throws SQLException {

        String insertQuery = "INSERT INTO supplydemand.accounts VALUES (?, ?, ?, ?, ?, ?)";
        //boolean mobileNumberExists = searchMobileNumber(acc.getMobileNumber());
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            //connection.setAutoCommit(false);
            for (AccountModel acc : batchlist) {
                //boolean mobileNumberExists = searchMobileNumber(acc.getMobileNumber());
                //if (!mobileNumberExists) {
                    insertStatement.setInt(Constants.FIRST_COL, acc.getAccountId());
                    insertStatement.setString(Constants.SECOND_COL, acc.getMobileNumber());
                    insertStatement.setString(Constants.THIRD_COL, acc.getFullName());
                    insertStatement.setString(Constants.FOURTH_COL, acc.getEmail());
                    insertStatement.setString(Constants.FIFITH_COL, acc.getAddress());
                    insertStatement.setString(Constants.SIXTH_COL, acc.getCardNumber());
                    insertStatement.addBatch();
                //}
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




