import dao.*;
import dao.impl.AuthorDaoImpl;
import dao.impl.BookDaoImpl;
import dao.impl.UserDaoImpl;
import domain.Role;
import domain.User;
import service.BookService;
import service.PasswordEncoder;
import service.UserService;
import service.impl.BookServiceImpl;
import service.impl.UserServiceImpl;
import service.mapper.AuthorMapper;
import service.mapper.BookMapper;
import service.mapper.UserMapper;
import service.validator.UserInputValidator;

public class Main {
    public static void main(String[] args) {
        User user = User.builder()
                .withName("sdfsdf")
                .withEmail("sdf@g.com")
                .withPassword("12345")
                .withPhoneNumber("+38067329")
                .withRole(Role.ADMIN).build();

        UserDao userDao = new UserDaoImpl();
        AuthorDao authorDao = new AuthorDaoImpl();
        BookDao bookDao = new BookDaoImpl(authorDao);
        UserInputValidator inputValidator = new UserInputValidator();
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        final UserService userService = new UserServiceImpl(passwordEncoder, userDao, new UserMapper(), inputValidator);
        final BookService bookService = new BookServiceImpl(bookDao, new BookMapper(new AuthorMapper()));

    }
}
