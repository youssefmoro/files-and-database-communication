package org.example.dataBaseCommunication;

import org.example.models.AccountModel;
import org.example.utils.Constants;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.example.appLogic.Accounts.accountsParser;

public class AccountsCommDb {
    static String jdbcUrl = "jdbc:mysql://localhost:3306/supplydemand";
    static String user = "root";
    static String password = "Ym@01061905439";
    static String folderPath = "D:\\task1 24-1-2024\\Accounts Folder";
    public static void accountsConnector(){
    try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password)) {
        List<AccountModel> accList;
        try {
            accList = accountsParser(folderPath);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        transferAccountsListToMysql(connection,accList);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    private static void transferAccountRecordToMysql (Connection connection, AccountModel acc) {
        String insertQuery = "INSERT INTO supplydemand.accounts VALUES (?, ?, ?, ?, ?, ?)";
        boolean mobileNumberExists = searchMobileNumber(acc.getMobileNumber());
        if (!mobileNumberExists) {

                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setLong(Constants.FIRST_COL, acc.getAccountId());
                    insertStatement.setString(Constants.SECOND_COL, acc.getMobileNumber());
                    insertStatement.setString(Constants.THIRD_COL, acc.getFullName());
                    insertStatement.setString(Constants.FOURTH_COL, acc.getEmail());
                    insertStatement.setString(Constants.FIFITH_COL, acc.getAddress());
                    insertStatement.setString(Constants.SIXTH_COL, acc.getCardNumber());
                    insertStatement.executeUpdate();
                    insertStatement.addBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
            private static void transferAccountsListToMysql (Connection connection, List < AccountModel > list){

                for (AccountModel accountModel : list) {
                    transferAccountRecordToMysql(connection, accountModel);
                }

            }

        private static boolean searchMobileNumber (String mobileNumber){
            try (Connection connection = DriverManager.getConnection(jdbcUrl,user,password)) {
                String query = "SELECT COUNT(*) FROM supplydemand.accounts WHERE mobileNumber = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, mobileNumber);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            int count = resultSet.getInt(1);
                            return count > 0;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Constants.FALSE;
        }

    }




