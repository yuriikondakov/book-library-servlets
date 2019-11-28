package dao.impl;

import dao.AuthorDao;
import dao.entity.AuthorEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AuthorDaoImpl extends AbstractCrudDaoImpl<AuthorEntity> implements AuthorDao {
    private static final String FIND_AUTHOR_BY_BOOK_ID_QUERY = "SELECT * from author JOIN " +
            "book_authors ON author.id=book_authors.authorId WHERE book_authors.bookId = ?";
    private static final String FIND_ALL_AUTHORS_QUERY = "SELECT * from author";
    private static final String FIND_AUTHOR_BY_ID_QUERY = "SELECT * from author WHERE id = ?";

    @Override
    protected AuthorEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return  new AuthorEntity(
                resultSet.getInt("id"),
                resultSet.getString("firstName"),
                resultSet.getString("LastName")
        );
    }

    @Override
    public List<AuthorEntity> findByBookId(Integer id) {
        return findItemsById(id, FIND_AUTHOR_BY_BOOK_ID_QUERY);
    }

    @Override
    public AuthorEntity save(AuthorEntity entity) {
        return null;
    }

    @Override
    public Optional<AuthorEntity> findById(Integer id) {
        return findById(id, FIND_AUTHOR_BY_ID_QUERY);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return findAll(FIND_ALL_AUTHORS_QUERY);
    }

    @Override
    public void update(AuthorEntity entity) {
    }

}
