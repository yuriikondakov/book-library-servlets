package dao.impl;

import dao.DataBaseRuntimeException;
import dao.UserDao;
import dao.entity.UserEntity;
import domain.Role;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractCrudDaoImpl<UserEntity> implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    private static final String FIND_BY_EMAIL_QUERY = "SELECT * from user WHERE email = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * from user WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * from user";
    private static final String SQL_INSERT_USER = "INSERT INTO user(name, email, password, phone_number, role) VALUES(?,?,?,?,?)";

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return findItemByStringParam(email, FIND_BY_EMAIL_QUERY);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        LOGGER.error("saving user");
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {
            preparedStatement.setString(1, userEntity.getName());
            preparedStatement.setString(2, userEntity.getEmail());
            preparedStatement.setString(3, userEntity.getPassword());
            preparedStatement.setString(4, userEntity.getPhoneNumber());
            preparedStatement.setString(5, userEntity.getRole().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't save user");
            throw new DataBaseRuntimeException(e);
        }
        return userEntity;
    }

    @Override
    public Optional<UserEntity> findById(Integer id) {
        return findById(id, FIND_BY_ID_QUERY);
    }

    @Override
    public List<UserEntity> findAll() {
        return findAll(FIND_ALL_QUERY);
    }

    @Override
    public UserEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
      //  System.out.println("mapUser" + resultSet.toString());
        return UserEntity.builder()
                .withId(resultSet.getInt("id"))
                .withName(resultSet.getString("name"))
                .withEmail(resultSet.getString("email"))
                .withPassword(resultSet.getString("password"))
                .withPhoneNumber(resultSet.getString("phone_number"))
                .withRole(Role.valueOf(resultSet.getString("role")))
                .build();
    }
}
