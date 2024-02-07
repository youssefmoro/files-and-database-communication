package org.example.validation;
import org.example.dataBaseCommunication.ConnectionIntializer;
import org.example.utils.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.example.utils.Constants.FIRST_COL;

public class Validation {
    //public List<Long> accountIds
    public static boolean stringAllCharValidator(String passedString)
    {
        boolean containsNumber = passedString.matches(".*\\d+.*");
        return containsNumber;
    }
    public static boolean allNumericValidatorInString(String passedString)
    {
        Boolean allNumericApproved = passedString.matches("\\d+");
        return allNumericApproved;
    }
    public static boolean floatingNumberValidatorInString(String passedString)
    {
        String stringPattern = "^\\d+(\\.\\d+)?$";
        Boolean allFloatApproved=passedString.matches(stringPattern);
        return allFloatApproved;
    }
    public static boolean mobileNumberValidatorInString(String passedString)
    {
        if(allNumericValidatorInString(passedString)&&(passedString.length()==Constants.MOBILE11DIGITS)) {
            return true;
        }
        return false;
    }
    public static boolean emailValidator(String passedString)
    {
        String email = "user@example.com";
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        boolean isValid = email.matches(regex);
        return isValid;
    }
    public static boolean searchOrderNumber (long orderNumber) throws SQLException {
        Connection connection= ConnectionIntializer.getConnection();
        String query = "SELECT COUNT(*) FROM supplydemand.orders WHERE orderNumber = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(FIRST_COL, orderNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(FIRST_COL);
                    connection.close();
                    return count > 0;
                }
            }
        }
        connection.close();
        return Constants.FALSE;
    }
    public static boolean searchMobileNumber (String mobileNumber) throws SQLException {
        Connection connection=ConnectionIntializer.getConnection();
        String query = "SELECT COUNT(*) FROM supplydemand.accounts WHERE mobileNumber = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, mobileNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    connection.close();
                    return count > 0;
                }
            }
        }
        connection.close();
        return Constants.FALSE;
    }


}
