package service;

import domain.User;

import java.util.List;

public interface UserService {

    User findById(Integer id);

    User register(User user);

    User login(String email, String password);

    boolean isExistEmail(String email);

    boolean isValidEmail(String email);

    boolean isValidPassword(String password);

    List<User> findAll();
}
