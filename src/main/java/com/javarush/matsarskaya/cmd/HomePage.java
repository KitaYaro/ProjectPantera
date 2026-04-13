package com.javarush.matsarskaya.cmd;

import jakarta.servlet.http.HttpServletRequest;

import static com.javarush.matsarskaya.config.ApplicationConstants.VIEW_HOME;

public class HomePage implements Command{

    @Override
    public String doGet(HttpServletRequest request) {
        return getView();
    }
    @Override
    public String doPost(HttpServletRequest request) {
        return getView();
    }
    @Override
    public String getView() {
        return VIEW_HOME;
    }
}
