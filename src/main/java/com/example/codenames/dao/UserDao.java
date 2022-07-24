package com.example.codenames.dao;

import com.example.codenames.Model.User;
import com.example.codenames.dto.UserCredentialsDto;

public interface UserDao {
    User registerUser(User user);
    User getByUserName(String username);
}
