package com.javarush.matsarskaya.service;

import com.javarush.matsarskaya.entity.User;
import com.javarush.matsarskaya.exception.InvalidCredentialsException;
import com.javarush.matsarskaya.exception.UserAlreadyExistsException;
import com.javarush.matsarskaya.exception.UserNotFoundException;
import com.javarush.matsarskaya.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        logger.info("UserService is initialized");
    }

    public void registerUser(String username, String password) {
        logger.info("User Registration Attempt: {}", username);

        if (userRepository.existsByUsername(username)) {
            logger.warn("The user named {} already exists", username);
            throw new UserAlreadyExistsException(username);
        }

        User newUser = new User(username, password);
        userRepository.save(newUser);
        logger.info("User {} successfully registered", username);
    }

    public Optional<User> loginUser(String username, String password) {
        logger.info("User Login Attempt: {}", username);
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            logger.warn("User {} not found", username);
            throw new UserNotFoundException(username);
        }

        if (!user.get().getPassword().equals(password)) {
            logger.warn("Invalid password for the user: {}", username);
            throw new InvalidCredentialsException();
        }
        logger.info("User {} successfully logged in", username);
        return user;
    }

    public static boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        boolean authenticated = session != null && session.getAttribute("username") != null;

        if (authenticated) {
            String username = (String) session.getAttribute("username");
            LoggerFactory.getLogger(UserService.class).debug("Authorization check: user {} is authorized", username);
        }

        return authenticated;
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute("username");
            session.invalidate();
            logger.info("User {} logged out", username);
        } else {
            logger.debug("Attempt to log out without an active session");
        }
    }
}
