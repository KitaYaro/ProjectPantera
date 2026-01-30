package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.exception.InvalidCredentialsException;
import com.javarush.matsarskaya.exception.UserNotFoundException;
import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage implements Command {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    private final UserService userService;

    public LoginPage(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        logger.debug("Displaying the login page");
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.info("Processing the POST login request for the user: {}", username);

        try {
            userService.loginUser(username, password);
            HttpSession session = request.getSession();
            logger.info("Successful user login: {}", username);
            session.setAttribute("username", username);
            return "/home-page";
        } catch (UserNotFoundException e) {
            logger.warn("Failed login attempt: user {} not found", username);
            request.setAttribute("error", "User not found");
        } catch (InvalidCredentialsException e) {
            logger.warn("Failed login attempt: invalid password for user {}", username);
            request.setAttribute("error", "Invalid username or password");
        } catch (Exception e) {
        logger.error("Unexpected error when user logs in {}: {}", username, e.getMessage(), e);
        request.setAttribute("error", "Error occurred when logging in");
    }
        return getView();
    }

    @Override
    public String getView() {
        return "/WEB-INF/login-page.jsp";
    }
}
