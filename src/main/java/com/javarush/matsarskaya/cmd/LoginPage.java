package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.exception.InvalidCredentialsException;
import com.javarush.matsarskaya.exception.UserNotFoundException;
import com.javarush.matsarskaya.service.IUserService;
import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.javarush.matsarskaya.config.ApplicationConstants.*;

public class LoginPage implements Command {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    private final IUserService userService;

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
        String username = request.getParameter(PARAM_USERNAME);
        String password = request.getParameter(PARAM_PASSWORD);
        logger.info("Processing the POST login request for the user: {}", username);

        try {
             userService.loginUser(username, password);
             HttpSession session = request.getSession();
             logger.info("Successful user login: {}", username);
             session.setAttribute(SESSION_USERNAME, username);
             return PATH_HOME;
        } catch (UserNotFoundException e) {
            logger.warn("Failed login attempt: user {} not found", username);
            request.setAttribute("error", ERROR_USER_NOT_FOUND);
        } catch (InvalidCredentialsException e) {
            logger.warn("Failed login attempt: invalid password for user {}", username);
            request.setAttribute("error", ERROR_INVALID_CREDENTIALS);
        } catch (Exception e) {
        logger.error("Unexpected error when user logs in {}: {}", username, e.getMessage(), e);
        request.setAttribute("error", ERROR_LOGGING_IN);
    }
        return getView();
    }

    @Override
    public String getView() {
        return VIEW_LOGIN;
    }
}
