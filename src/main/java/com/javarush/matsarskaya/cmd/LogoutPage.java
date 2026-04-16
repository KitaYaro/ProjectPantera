package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.service.IUserService;
import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

import static com.javarush.matsarskaya.config.ApplicationConstants.PATH_HOME;

public class LogoutPage implements Command{
    private final IUserService userService;

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
        return PATH_HOME;
    }
}
