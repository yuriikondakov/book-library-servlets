package service.impl;

import dao.UserDao;
import dao.entity.UserEntity;
import domain.Role;
import domain.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import service.PasswordEncoder;
import service.mapper.UserMapper;
import service.validator.UserInputValidator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceImplTest {
    private static final UserEntity USER_ENTITY = initUserEntity();
    private static final User USER = initUser();

    @Mock
    private UserDao userDao;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserInputValidator validator;

    @InjectMocks
    UserServiceImpl userService;

    @After
    public void setAfter() {
        reset(userDao);
    }

    @Test
    public void findByIdShouldFindById() {
        when(userDao.findById(any(Integer.class))).thenReturn(Optional.ofNullable(USER_ENTITY));

        User expected = userService.findById(25);
        assert USER_ENTITY != null;
        User actual = userMapper.mapUserEntityToUser(USER_ENTITY);

        assertThat(actual, is(expected));
    }

    @Test
    public void registerShouldReturnUser() {
        when(passwordEncoder.encode(any(String.class))).thenReturn("12345");
        when(userMapper.mapUserToUserEntity(any(User.class))).thenReturn(USER_ENTITY);
        when(userMapper.mapUserEntityToUser(any(UserEntity.class))).thenReturn(USER);
        when(userDao.save(any(UserEntity.class))).thenReturn(USER_ENTITY);

        User expected = userService.register(USER);

        assertThat(USER, is(expected));
        verify(userDao).save(USER_ENTITY);
        verify(userMapper).mapUserToUserEntity(USER);
    }

    @Test
    public void loginShouldReturnOptionalUser() {
        when(validator.isValidEmail(any(String.class))).thenReturn(true);
        when(validator.isValidPassword(any(String.class))).thenReturn(true);
        when(passwordEncoder.checkPassword(any(String.class),any(String.class))).thenReturn(true);
        when(userDao.findByEmail(any(String.class))).thenReturn(Optional.ofNullable((USER_ENTITY)));
        when(userMapper.mapUserEntityToUser(any(UserEntity.class))).thenReturn(USER);

        User expected = userService.login("testuser@gmail.com" , "12345");

        assertThat(USER, is(expected));
        verify(validator).isValidPassword(any(String.class));
        verify(userDao).findByEmail(any(String.class));
        verify(userMapper).mapUserEntityToUser(USER_ENTITY);
    }

    @Test
    public void loginShouldReturnOptionalEmpty() {
        when(validator.isValidEmail(any(String.class))).thenReturn(false);
        when(validator.isValidPassword(any(String.class))).thenReturn(true);
        when(passwordEncoder.checkPassword(any(String.class),any(String.class))).thenReturn(true);
        when(userDao.findAll()).thenReturn(Collections.singletonList(USER_ENTITY));
        when(userMapper.mapUserEntityToUser(any(UserEntity.class))).thenReturn(USER);

        User expected = userService.login("testuser@gmail.com" , "12345");

        assertThat(null, is(expected));
        verify(validator).isValidEmail(any(String.class));
    }

    @Test
    public void findAll() {
        when(userDao.findAll()).thenReturn(Collections.singletonList(USER_ENTITY));
        when(userMapper.mapUserEntityToUser(any(UserEntity.class))).thenReturn(USER);

        List<User> expected = userService.findAll();

        assertThat(Collections.singletonList(USER), is(expected));
        verify(userDao).findAll();
    }

    @Test
    public void isExistEmailShouldReturnTrue() {
        when(userDao.findByEmail("testuser@gmail.com")).thenReturn(Optional.ofNullable(USER_ENTITY));

        boolean expected = userService.isExistEmail("testuser@gmail.com");
        boolean actual = true;

        assertThat(actual, is(expected));
    }

    @Test
    public void isExistEmailShouldReturnFalse() {
        when(userDao.findByEmail("testuser@gmail.ua")).thenReturn(Optional.empty());
        boolean expected = userService.isExistEmail("testuser@gmail.ua");
        boolean actual = false;
        assertThat(actual, is(expected));
    }


    private static UserEntity initUserEntity() {
        return UserEntity.builder()
                .withId(25)
                .withName("TestUser")
                .withEmail("testuser@gmail.com")
                .withPassword("MTIzNDU=")
                .withPhoneNumber("+3806722233222")
                .withRole(Role.USER).build();
    }

    private static User initUser() {
        return User.builder()
                .withId(25)
                .withName("TestUser")
                .withEmail("testuser@gmail.com")
                .withPassword("MTIzNDU=")
                .withPhoneNumber("+3806722233222")
                .withRole(Role.USER).build();
    }
}
