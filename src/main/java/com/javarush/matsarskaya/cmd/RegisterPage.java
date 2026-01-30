package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.exception.UserAlreadyExistsException;
import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class RegisterPage implements Command{
    private final UserService userService;

    public RegisterPage(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            userService.registerUser(username, password);
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            return "/home-page";
        } catch (UserAlreadyExistsException e) {
            request.setAttribute("error", "The user already exists");
        }

        return getView();
    }

    @Override
    public String getView() {
        return "/WEB-INF/register-page.jsp";
    }
}
