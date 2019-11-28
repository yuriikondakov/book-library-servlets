package dao;

import dao.entity.UserEntity;

import java.util.Optional;

public interface UserDao extends CrudDao<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
}
