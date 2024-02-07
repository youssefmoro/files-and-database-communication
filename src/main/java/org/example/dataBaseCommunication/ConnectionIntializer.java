package org.example.dataBaseCommunication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.example.properties.Config;

public class ConnectionIntializer {

    public static Connection getConnection () throws SQLException {
        Connection connection = DriverManager.getConnection(Config.JDBCURL, Config.USER, Config.PASSWORD);
        return connection;

    }
}
