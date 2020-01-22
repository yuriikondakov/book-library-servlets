package service.impl;

import dao.UserDao;
import dao.entity.UserEntity;
import domain.User;
import org.apache.log4j.Logger;
import service.PasswordEncoder;
import service.UserService;
import service.mapper.UserMapper;
import service.validator.UserInputValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final UserInputValidator validator;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserDao userDao,
                           UserMapper userMapper, UserInputValidator validator) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.validator = validator;
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id).map(userMapper::mapUserEntityToUser).orElse(null);
    }

    @Override
    public User register(User user) {
        LOGGER.debug("start register");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity savedUserEntity = userDao.save(userMapper.mapUserToUserEntity(user));
        LOGGER.debug("UserEntity saved.");
        return userMapper.mapUserEntityToUser(savedUserEntity);
    }

    @Override
    public User login(String email, String password) {
        if (isValidEmail(email) && isValidPassword(password)) {
            Optional<User> optionalUser = userDao.findByEmail(email).map(userMapper::mapUserEntityToUser);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (passwordEncoder.checkPassword(password, user.getPassword())) {
                    LOGGER.debug("user login and password ok");
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isExistEmail(String email) {
        return userDao.findByEmail(email).isPresent();
    }

    @Override
    public boolean isValidEmail(String email) {
        return validator.isValidEmail(email);
    }

    @Override
    public boolean isValidPassword(String password) {
        return validator.isValidPassword(password);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll().stream().map(userMapper::mapUserEntityToUser).collect(Collectors.toList());
    }
}
