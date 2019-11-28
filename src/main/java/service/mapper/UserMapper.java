package service.mapper;

import dao.entity.UserEntity;
import domain.User;

public class UserMapper {

    public User mapUserEntityToUser(UserEntity userEntity) {
        return User.builder()
                .withId(userEntity.getId())
                .withName(userEntity.getName())
                .withEmail(userEntity.getEmail())
                .withPassword(userEntity.getPassword())
                .withPhoneNumber(userEntity.getPhoneNumber())
                .withRole(userEntity.getRole())
                .build();
    }

    public UserEntity mapUserToUserEntity(User user) {
        return UserEntity.builder()
                .withId(user.getId())
                .withName(user.getName())
                .withEmail(user.getEmail())
                .withPassword(user.getPassword())
                .withPhoneNumber(user.getPhoneNumber())
                .withRole(user.getRole())
                .build();
    }
}
