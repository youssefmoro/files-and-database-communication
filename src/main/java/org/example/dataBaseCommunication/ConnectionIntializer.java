package org.example.dataBaseCommunication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.example.properties.Config;
import com.zaxxer.hikari.HikariDataSource;

import static org.example.properties.Config.CONNECTION_POOL_SIZE;


public class ConnectionIntializer {
    private static final HikariDataSource dataSource = new HikariDataSource();

    static {
        dataSource.setMaximumPoolSize(CONNECTION_POOL_SIZE);
    }
    public static Connection getConnection () throws SQLException {
        Connection connection =dataSource.getConnection();
       // Connection connection = DriverManager.getConnection(Config.JDBCURL, Config.USER, Config.PASSWORD);
        return connection;

    }
}
