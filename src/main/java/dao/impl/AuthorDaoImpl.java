package dao.impl;

import dao.AuthorDao;
import dao.DataBaseRuntimeException;
import dao.entity.AuthorEntity;

import java.sql.*;
import org.apache.log4j.Logger;
import java.util.List;
import java.util.Optional;

public class AuthorDaoImpl extends AbstractCrudDaoImpl<AuthorEntity> implements AuthorDao {
    private static final Logger LOGGER = Logger.getLogger(AuthorDaoImpl.class);

    private static final String FIND_AUTHOR_BY_BOOK_ID_QUERY = "SELECT * from author JOIN book_author ON " +
            "author.id=book_author.author_id WHERE book_author.book_id = ?";
    private static final String FIND_ALL_AUTHORS_QUERY = "SELECT * from author";
    private static final String FIND_AUTHOR_BY_ID_QUERY = "SELECT * from author WHERE id = ?";
    private static final String INSERT_AUTHOR_QUERY = "INSERT INTO author (first_name, last_name) VALUES (?,?)";

    @Override
    protected AuthorEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return  new AuthorEntity(
                resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("Last_name")
        );
    }

    @Override
    public List<AuthorEntity> findByBookId(Integer id) {
        return findItemsById(id, FIND_AUTHOR_BY_BOOK_ID_QUERY);
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(INSERT_AUTHOR_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, authorEntity.getFirstName());
            preparedStatement.setString(2, authorEntity.getLastName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (!resultSet.next()) {
                throw new DataBaseRuntimeException("Generator keys problem");
            } else {
                return findById(resultSet.getInt(1)).orElse(null);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't save authorEntity");
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public Optional<AuthorEntity> findById(Integer id) {
        return findById(id, FIND_AUTHOR_BY_ID_QUERY);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return findAll(FIND_ALL_AUTHORS_QUERY);
    }

}
