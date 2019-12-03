package dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    private static final BasicDataSource dataSource = new BasicDataSource();

    public ConnectionPool(String dbProperties) {
        ResourceBundle resource = ResourceBundle.getBundle(dbProperties);
        dataSource.setDriverClassName(resource.getString("driver"));
        dataSource.setUrl(resource.getString("url"));
        dataSource.setUsername(resource.getString("user"));
        dataSource.setPassword(resource.getString("password"));
        dataSource.setMinIdle(getParameter(resource, "MinIdle"));
        dataSource.setMaxIdle(getParameter(resource, "MaxIdle"));
        dataSource.setMaxOpenPreparedStatements(getParameter(resource, "MaxOpenPreparedStatements"));
    }

    private int getParameter(ResourceBundle resource, String param) {
        return Integer.parseInt(resource.getString(param));
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Connection to database was not established", e);
            throw new DataBaseRuntimeException("Connection to database not established", e);
        }
    }

}
