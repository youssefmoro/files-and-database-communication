package org.example.periodicity.commonTasks;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.example.properties.Config.*;

public class ConnectionIntializerTask {
    public static final HikariDataSource dataSource = new HikariDataSource();

    static {
        dataSource.setJdbcUrl(JDBCURL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(CONNECTION_POOL_SIZE);
        // dataSource.setIdleTimeout(IDLE_TIMEOUT);
        // dataSource.setConnectionTimeout(CONNECTION_TIMEOUT);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    }
        public static Connection getConnection () throws SQLException {
           // Connection connection = DriverManager.getConnection(Config.JDBCURL, Config.USER, Config.PASSWORD);
            Connection connection = null;
            connection = dataSource.getConnection();
            return connection;
        }
    }