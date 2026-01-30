package com.javarush.matsarskaya.cmd;

import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    default String doGet(HttpServletRequest request) {
        return getView();
    }

    default String doPost(HttpServletRequest request) {
        return getView();
    }

    String getView();
}
