package dao.impl;

import dao.AuthorDao;
import dao.BookDao;
import dao.DataBaseRuntimeException;
import dao.entity.AuthorEntity;
import dao.entity.BookEntity;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDaoImpl extends AbstractCrudDaoImpl<BookEntity> implements BookDao {
    private static final Logger LOGGER = Logger.getLogger(BookDaoImpl.class);
    private static final String FIND_ALL_QUERY = "SELECT * from book";
    private static final String FIND_BY_ID_QUERY = "SELECT * from book WHERE id = ?";
    private static final String FIND_BOOKS_BY_USER_ID_QUERY = "SELECT book.id, book.name, book.description, book.shelfId," +
            " book_tracking.issue_date, book_tracking.return_date from book " +
            "JOIN book_tracking ON book_tracking.bookId = book.id " +
            "WHERE book_tracking.userId = ? and book_tracking.return_date IS NULL ";
    private static final String RETURN_BOOK_QUERY = "UPDATE book_tracking SET return_date=? " +
            "WHERE book_tracking.userId = ? and  book_tracking.bookId=?";
    private static final String FIND_RAWS_COUNT_QUERY = "SELECT COUNT(id) from book";
    private static final String FIND_BOOKS_PER_PAGE = "SELECT * FROM book LIMIT ?, ?";
    private static final String FIND_BOOKS_WITH_SEARCH_PER_PAGE = "SELECT * FROM book WHERE book.name LIKE ? LIMIT ?, ?";
    private static final String INSERT_BOOK_TRACKING_QUERY = "INSERT INTO book_tracking(userId, bookId, issue_date) " +
            "VALUES (?,?,?)";
    private static final String INSERT_BOOK_QUERY = "INSERT INTO book (name, description) VALUES (?,?)";
    private static final String INSERT_BOOK_AUTHORS_QUERY = "INSERT INTO book_authors (bookId, authorId) VALUES (?,?)";

    private final AuthorDao authorDao;

    public BookDaoImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public int findRowsCount() {
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(FIND_RAWS_COUNT_QUERY)) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't find by id");
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public List<BookEntity> findBooksPerPage(int currentPage, int recordsPerPage) {
        int start = currentPage * recordsPerPage - recordsPerPage;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BOOKS_PER_PAGE)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, recordsPerPage);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<BookEntity> entities = new ArrayList<>();
                while (resultSet.next()) {
                    entities.add(mapResultSetToEntity(resultSet));
                }
                return entities;
            }
        } catch (SQLException e) {
            LOGGER.error("Can't find by id");
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public List<BookEntity> findBooksByStringParamPerPage(int currentPage, int recordsPerPage, String searchField) {
        int start = currentPage * recordsPerPage - recordsPerPage;
        LOGGER.debug(searchField + "   " + start + "   " + recordsPerPage);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BOOKS_WITH_SEARCH_PER_PAGE)) {
            preparedStatement.setString(1, "%" + searchField + "%");
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, recordsPerPage);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<BookEntity> entities = new ArrayList<>();
                while (resultSet.next()) {
                    entities.add(mapResultSetToEntity(resultSet));
                }
                return entities;
            }
        } catch (SQLException e) {
            LOGGER.error("Can't find books by string param");
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public BookEntity saveBook(BookEntity bookEntity) {
        LOGGER.info("saving book");
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(INSERT_BOOK_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, bookEntity.getName());
            preparedStatement.setString(2, bookEntity.getDescription());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (!resultSet.next()) {
                LOGGER.error("Can't save bookEntity");
                throw new DataBaseRuntimeException("Generator keys problem");
            } else {
                return findById(resultSet.getInt(1)).orElse(null);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't save bookEntity");
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public void saveBookAuthors(Integer bookId, Integer authorId) {
        LOGGER.info("saving book_authors");
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK_AUTHORS_QUERY)) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, authorId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't save book_authors");
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public boolean saveBookTracking(Integer userId, Integer bookId, LocalDate now) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK_TRACKING_QUERY)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);
            preparedStatement.setDate(3, Date.valueOf(now));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error("Can't insert into book_tracking");
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public List<BookEntity> findBooksByUserId(Integer userId) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BOOKS_BY_USER_ID_QUERY)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<BookEntity> entities = new ArrayList<>();
                while (resultSet.next()) {
                    entities.add(mapUserBookSetToEntity(resultSet));
                }
                return entities;
            }
        } catch (SQLException e) {
            LOGGER.error("Can't find by userId");
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public boolean updateBookTracking(Integer userId, Integer bookId, LocalDate now) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(RETURN_BOOK_QUERY)) {
            preparedStatement.setDate(1, Date.valueOf(now));
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, bookId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error("Can't insert into book_tracking");
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    protected BookEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int bookId = resultSet.getInt("id");
        List<AuthorEntity> authorEntityList = authorDao.findByBookId(bookId);
        return BookEntity.builder()
                .withId(resultSet.getInt("id"))
                .withName(resultSet.getString("name"))
                .withAuthors(authorEntityList)
                .withDescription(resultSet.getString("description"))
                .withShelfId(resultSet.getInt("shelfId"))
                .build();
    }

    private BookEntity mapUserBookSetToEntity(ResultSet resultSet) throws SQLException {
        int bookId = resultSet.getInt("id");
        List<AuthorEntity> authorEntityList = authorDao.findByBookId(bookId);
        return BookEntity.builder()
                .withId(resultSet.getInt("id"))
                .withName(resultSet.getString("name"))
                .withAuthors(authorEntityList)
                .withDescription(resultSet.getString("description"))
                .withShelfId(resultSet.getInt("shelfId"))
                .withIssueDate(resultSet.getDate("issue_date").toLocalDate())
                .build();
    }

    @Override
    public BookEntity save(BookEntity entity) {
        return null;
    }

    @Override
    public Optional<BookEntity> findById(Integer id) {
        return findById(id, FIND_BY_ID_QUERY);
    }

    @Override
    public List<BookEntity> findAll() {
        return findAll(FIND_ALL_QUERY);
    }

    @Override
    public void update(BookEntity entity) {
    }

}
