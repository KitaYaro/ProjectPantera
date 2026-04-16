package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.exception.UserAlreadyExistsException;
import com.javarush.matsarskaya.service.IUserService;
import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.javarush.matsarskaya.config.ApplicationConstants.*;

public class RegisterPage implements Command{
    private final IUserService userService;

    public RegisterPage(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest request) {
        String username = request.getParameter(PARAM_USERNAME);
        String password = request.getParameter(PARAM_PASSWORD);

        try {
            userService.registerUser(username, password);
            HttpSession session = request.getSession();
            session.setAttribute(SESSION_USERNAME, username);
            return PATH_HOME;
        } catch (UserAlreadyExistsException e) {
            request.setAttribute("error", ERROR_USER_ALREADY_EXISTS);
        }

        return getView();
    }

    @Override
    public String getView() {
        return VIEW_REGISTER;
    }
}
