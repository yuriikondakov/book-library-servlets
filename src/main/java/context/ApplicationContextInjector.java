package context;

import dao.AuthorDao;
import dao.BookDao;
import dao.UserDao;
import dao.impl.AuthorDaoImpl;
import dao.impl.BookDaoImpl;
import dao.impl.UserDaoImpl;
import service.AuthorService;
import service.BookService;
import service.PasswordEncoder;
import service.UserService;
import service.impl.AuthorServiceImpl;
import service.impl.BookServiceImpl;
import service.impl.UserServiceImpl;
import service.mapper.AuthorMapper;
import service.mapper.BookMapper;
import service.mapper.UserMapper;
import service.validator.UserInputValidator;
import web.command.Command;
import web.command.book.*;
import web.command.user.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContextInjector {
    private final static AuthorDao AUTHOR_DAO = new AuthorDaoImpl();
    private final static BookDao BOOK_DAO = new BookDaoImpl(AUTHOR_DAO);
    private static final UserDao USER_DAO = new UserDaoImpl();

    private final static AuthorMapper AUTHOR_MAPPER = new AuthorMapper();
    private final static BookMapper BOOK_MAPPER = new BookMapper(AUTHOR_MAPPER);

    private final static BookService BOOK_SERVICE = new BookServiceImpl(BOOK_DAO, BOOK_MAPPER);
    private final static AuthorService AUTHOR_SERVICE = new AuthorServiceImpl(AUTHOR_DAO, AUTHOR_MAPPER);

    private static final UserMapper USER_MAPPER = new UserMapper();
    private static final UserInputValidator USER_INPUT_VALIDATOR = new UserInputValidator();
    private static final PasswordEncoder PASSWORD_ENCODER = new PasswordEncoder();
    private final static UserService USER_SERVICE = new UserServiceImpl(PASSWORD_ENCODER, USER_DAO, USER_MAPPER,
            USER_INPUT_VALIDATOR);
    private static final Command LOGIN_COMMAND = new LoginCommand(USER_SERVICE);
    private static final Command LOGOUT_COMMAND = new LogoutCommand();
    private static final Command LOCALE_COMMAND = new LocaleCommand();
    private static final Command CATALOG_COMMAND = new CatalogCommand(BOOK_SERVICE);
    private static final Command REGISTER_COMMAND = new RegisterCommand(USER_SERVICE);
    private static final Command BOOK_COMMAND = new BookCommand(BOOK_SERVICE);
    private static final Command TAKE_BOOK_COMMAND = new TakeBookCommand(BOOK_SERVICE);
    private static final Command RETURN_BOOK_COMMAND = new ReturnBookCommand(BOOK_SERVICE);
    private static final Command USER_BOOKS_COMMAND = new UserBooksCommand(BOOK_SERVICE);
    private static final Command ADD_BOOK_COMMAND = new AddBookCommand(BOOK_SERVICE,  AUTHOR_SERVICE);
    private static final Map<String, Command> USER_COMMAND_NAME_TO_COMMAND = initUserCommand();

    private static Map<String, Command> initUserCommand() {
        Map<String, Command> userCommandNameToCommand = new HashMap<>();
        userCommandNameToCommand.put("login", LOGIN_COMMAND);
        userCommandNameToCommand.put("logout", LOGOUT_COMMAND);
        userCommandNameToCommand.put("register", REGISTER_COMMAND);
        userCommandNameToCommand.put("catalog", CATALOG_COMMAND);
        userCommandNameToCommand.put("book", BOOK_COMMAND);
        userCommandNameToCommand.put("takeBook", TAKE_BOOK_COMMAND);
        userCommandNameToCommand.put("my_books", USER_BOOKS_COMMAND);
        userCommandNameToCommand.put("returnBook", RETURN_BOOK_COMMAND);
        userCommandNameToCommand.put("add_book", ADD_BOOK_COMMAND);
        userCommandNameToCommand.put("locale", LOCALE_COMMAND);

        return Collections.unmodifiableMap(userCommandNameToCommand);
    }

    private static ApplicationContextInjector instance;

    private ApplicationContextInjector() {
        // private constructor 
    }

    public static ApplicationContextInjector getInstance() {
        if (instance == null) {
            synchronized (ApplicationContextInjector.class) {
                if (instance == null) {
                    instance = new ApplicationContextInjector();
                }
            }
        }
        return instance;
    }

    public AuthorDao getAuthorDao() {
        return AUTHOR_DAO;
    }

    public BookDao getBookDao() {
        return BOOK_DAO;
    }

    public UserDao getUserDao() {
        return USER_DAO;
    }

    public AuthorMapper getAuthorMapper() {
        return AUTHOR_MAPPER;
    }

    public BookMapper getBookMapper() {
        return BOOK_MAPPER;
    }

    public BookService getBookService() {
        return BOOK_SERVICE;
    }

    public UserService getUserService() {
        return USER_SERVICE;
    }

    public Map<String, Command> getUserCommandNameToCommand() {
        return USER_COMMAND_NAME_TO_COMMAND;
    }

    public PasswordEncoder getPasswordEncoder() {
        return PASSWORD_ENCODER;
    }
}
