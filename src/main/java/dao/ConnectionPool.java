package dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    private BasicDataSource dataSource;

    public ConnectionPool() {
    }

    public Connection getConnection() {
        Connection connection;
        try {
            initDataSource("mysql");
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.warn("Check database is running and configs are correct");
            throw new DataBaseRuntimeException("Fail Connection", e);
        }
        catch (ClassNotFoundException e) {
            LOGGER.warn("Please include JDBC MySQL jar in classpath");
            throw new DataBaseRuntimeException("Fail Connection", e);
        }
        return connection;
    }

    private void initDataSource(String dbProperties) {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            ResourceBundle resource = ResourceBundle.getBundle(dbProperties);
            dataSource.setUrl(resource.getString("url"));
            dataSource.setUsername(resource.getString("user"));
            dataSource.setPassword(resource.getString("password"));
            dataSource.setMinIdle(Integer.parseInt(resource.getString("MinIdle")));
            dataSource.setMaxIdle(Integer.parseInt(resource.getString("MaxIdle")));
            dataSource.setMaxOpenPreparedStatements(Integer.parseInt(resource.getString("MaxOpenPreparedStatements")));
        }
    }
}
