package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutPage implements Command{
    private final UserService userService;

    public LogoutPage(UserService userService) {
            this.userService = userService;
        }

    @Override
    public String doPost(HttpServletRequest request) {
        userService.logout(request);
        return getView();
    }

    @Override
    public String doGet(HttpServletRequest request) {
        return doPost(request);
    }

    @Override
    public String getView() {
        return "/home-page";
    }
}
