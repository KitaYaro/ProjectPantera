package com.javarush.matsarskaya.service;

import com.javarush.matsarskaya.exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface IUserService {
    void registerUser(String username, String password) throws UserAlreadyExistsException;
    Optional loginUser(String username, String password);
    void logout(HttpServletRequest request);
    static boolean isAuthenticated(HttpServletRequest request){
        return UserService.isAuthenticated(request);
    }

}
