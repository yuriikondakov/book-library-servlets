package dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBCPDataSource {
    private static final Logger LOGGER = Logger.getLogger(DBCPDataSource.class);

    private static final BasicDataSource dataSource = new BasicDataSource();

    private DBCPDataSource (String dbProperties) {
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

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Connection to database was not established", e);
            throw new DataBaseRuntimeException("Connection to database not established", e);
        }
    }

}
