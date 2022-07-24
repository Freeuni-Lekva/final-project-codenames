package com.example.codenames.service;

import com.example.codenames.Model.User;
import com.example.codenames.dto.UserCredentialsDto;
import com.example.codenames.exception.InvalidCredentialsException;
import com.example.codenames.exception.UserRegistrationException;

public interface UserService {
    User registerUser(UserCredentialsDto credentials) throws UserRegistrationException;
    User loginUser(UserCredentialsDto credentials) throws InvalidCredentialsException;
}
