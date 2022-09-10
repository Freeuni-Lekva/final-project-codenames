package com.example.codenames.service;

import com.example.codenames.DTO.UserCredentialsDto;
import com.example.codenames.exception.InvalidCredentialsException;
import com.example.codenames.exception.UserNameRepeatedException;
import com.example.codenames.exception.UserRegistrationException;
import com.example.codenames.model.User;

import java.util.List;

public interface UserService {
    User registerUser(UserCredentialsDto credentials) throws UserRegistrationException, UserNameRepeatedException;
    User loginUser(UserCredentialsDto credentials) throws InvalidCredentialsException;
    User getUserByUsername(String userName);
    List<User> getUsersByPoints(Boolean flag);
    boolean updateUser(User user);
    boolean deleteUser(int userId);
}
