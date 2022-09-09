package com.example.codenames.DAO;

import com.example.codenames.DTO.UserCredentialsDto;
import com.example.codenames.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    User registerUser(User user);
    User getByUserName(String username);
    User loginUser(User user);
    List<User> getUsersByPoints(String order);
    boolean updateUser(User user);

    boolean deleteUser(int id);

}
