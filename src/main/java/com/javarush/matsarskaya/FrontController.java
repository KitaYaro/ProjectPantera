package com.javarush.matsarskaya;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/")
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Прямое перенаправление на JSP страницу
        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String choiceQuest =req.getParameter("quest");
        String message = "";

        if (choiceQuest.equals("the way of the dragon rider")) {
            message = "Вы выбрали квест путь всадника дракона";
        }
        req.setAttribute("message", message);
        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}


