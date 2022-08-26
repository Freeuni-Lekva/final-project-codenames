package com.example.codenames.service.implementation;

import com.example.codenames.DAO.UserDao;
import com.example.codenames.DTO.UserCredentialsDto;
import com.example.codenames.exception.InvalidCredentialsException;
import com.example.codenames.exception.UserNameRepeatedException;
import com.example.codenames.exception.UserPasswordException;
import com.example.codenames.exception.UserRegistrationException;
import com.example.codenames.model.User;
import com.example.codenames.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private final int  MINIMUM_PASSWORD_LENGTH = 8;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User registerUser(UserCredentialsDto credentials) throws UserRegistrationException,UserNameRepeatedException {
        //System.out.println(credentials.getUsername() + " " + credentials.getPassword());
        if(credentials.getPassword().length() < MINIMUM_PASSWORD_LENGTH){
            throw new UserPasswordException("");
        }
        User user = userDao.getByUserName(credentials.getUsername());
        if(user != null){
            throw new UserNameRepeatedException(credentials.getUsername());
        }
        user = new User(credentials.getUsername(), credentials.getPassword());

        User addedUser = userDao.registerUser(user);
        if(addedUser == null){
            throw new UserRegistrationException(credentials.getUsername());
        }
        return addedUser;
    }

    @Override
    public User loginUser(UserCredentialsDto credentials) throws InvalidCredentialsException {
        User user = new User(credentials.getUsername(), credentials.getPassword());
        User logedIn = userDao.loginUser(user);
        if(logedIn == null){
            throw new InvalidCredentialsException(credentials.getUsername());
        }
        System.out.println(logedIn);
        return logedIn;
    }

    @Override
    public User getUserByUsername(String userName) throws InvalidCredentialsException{
        User user = userDao.getByUserName(userName);
        if(user == null){
            throw new InvalidCredentialsException(userName);
        }
        System.out.println(user);
        return user;
    }
}
