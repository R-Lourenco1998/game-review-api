package com.gamereview.api.services;
import com.gamereview.api.entities.User;
import java.util.List;

public interface UserService {

        List<User> findAllUser();

        User createUser(User user);

        void deleteUser(Long id);

        User findUserById(Long id);

        User updateUser(Long id, User user);

}
