package com.javarush.matsarskaya.cmd;

import jakarta.servlet.http.HttpServletRequest;

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
        return "/WEB-INF/home-page.jsp";
    }
}
