package com.javarush.matsarskaya.controller;

import com.javarush.matsarskaya.cmd.*;
import com.javarush.matsarskaya.repository.*;
import com.javarush.matsarskaya.service.StatisticService;
import com.javarush.matsarskaya.service.UserService;
import com.javarush.matsarskaya.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.Map;

public class HttpResolver {
    private final Map<String, Command> commandMap;
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


    public HttpResolver() {
        UserRepository userRepository = new HibernateUserRepository(sessionFactory);
        StatisticRepository statisticRepository = new HibernateStatisticRepository(sessionFactory);
        UserService userService = new UserService(userRepository);
        StatisticService statisticService = new StatisticService(statisticRepository, userRepository);

        this.commandMap = Map.of(
                "/home-page", new HomePage(),
                "/quest-dragon", new QuestDragon(statisticService),
                "/login-page", new LoginPage(userService),
                "/register-page", new RegisterPage(userService),
                "/logout", new LogoutPage(userService),
                "/statistic-page", new StatisticPage(statisticService)
        );
    }

    public Command resolve(String pathInfo) {
        return commandMap.getOrDefault(pathInfo, new HomePage());
    }
}
