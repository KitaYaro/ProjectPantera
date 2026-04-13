package com.javarush.matsarskaya.controller;

import com.javarush.matsarskaya.cmd.*;
import com.javarush.matsarskaya.repository.*;
import com.javarush.matsarskaya.service.StatisticService;
import com.javarush.matsarskaya.service.UserService;
import com.javarush.matsarskaya.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.Map;

import static com.javarush.matsarskaya.config.ApplicationConstants.*;

public class HttpResolver {
    private final Map<String, Command> commandMap;

    public HttpResolver(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    public HttpResolver() {
        this(createDefaultCommandMap());
    }

    private static Map<String, Command> createDefaultCommandMap() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        UserRepository userRepository = new HibernateUserRepository(sessionFactory);
        StatisticRepository statisticRepository = new HibernateStatisticRepository(sessionFactory);
        UserService userService = new UserService(userRepository);
        StatisticService statisticService = new StatisticService(statisticRepository, userRepository);

        return Map.of(
                PATH_HOME, new HomePage(),
                PATH_LOGIN, new LoginPage(userService),
                PATH_REGISTER, new RegisterPage(userService),
                PATH_QUEST_DRAGON, new QuestDragon(statisticService),
                PATH_LOGOUT, new LogoutPage(userService),
                PATH_STATISTIC, new StatisticPage(statisticService)
        );
    }
    public Command resolve(String pathInfo) {
        return commandMap.getOrDefault(pathInfo, new HomePage());
    }
}

//    public HttpResolver() {
//        UserRepository userRepository = new HibernateUserRepository(sessionFactory);
//        StatisticRepository statisticRepository = new HibernateStatisticRepository(sessionFactory);
//        UserService userService = new UserService(userRepository);
//        StatisticService statisticService = new StatisticService(statisticRepository, userRepository);
//
//        this.commandMap = Map.of(
//                PATH_HOME, new HomePage(),
//                PATH_LOGIN, new LoginPage(userService),
//                PATH_REGISTER, new RegisterPage(userService),
//                PATH_QUEST_DRAGON, new QuestDragon(statisticService),
//                PATH_LOGOUT, new LogoutPage(userService),
//                PATH_STATISTIC, new StatisticPage(statisticService)
//        );
//    }
//


