package dao.impl;

import context.ApplicationContextInjector;
import dao.ConnectionPool;
import dao.CrudDao;
import dao.DataBaseRuntimeException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract class AbstractCrudDaoImpl<E> implements CrudDao<E, Integer> {
    private static final Logger LOGGER = Logger.getLogger(AbstractCrudDaoImpl.class);
    final ConnectionPool connectionPool = ApplicationContextInjector.getInstance().getConnectionPool();

    protected Optional<E> findById(Integer id, String query) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.ofNullable(mapResultSetToEntity(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception. Can't find by id");
            throw new DataBaseRuntimeException(e);
        }
    }

    List<E> findAll(String query) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            LOGGER.debug("start find all");
            return getEntities(preparedStatement);
        } catch (SQLException e) {
            LOGGER.error("Can't find all" + query);
            throw new DataBaseRuntimeException(e);
        }
    }

    List<E> findItemsById(Integer id, String query) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            return getEntities(preparedStatement);
        } catch (SQLException e) {
            LOGGER.error("Can't find Items by id: " + query);
            throw new DataBaseRuntimeException(e);
        }
    }

    private List<E> getEntities(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            List<E> entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(mapResultSetToEntity(resultSet));
            }
            return entities;
        }
    }

    protected Optional<E> findItemByStringParam(String param, String query) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, param);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                return resultSet.next() ? Optional.ofNullable(mapResultSetToEntity(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.error("Can't find by " + param);
            throw new DataBaseRuntimeException(e);
        }
    }

    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;
}
