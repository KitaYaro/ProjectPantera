package com.javarush.matsarskaya.controller;

import com.javarush.matsarskaya.cmd.*;
import com.javarush.matsarskaya.entity.UserFileStorage;
import com.javarush.matsarskaya.repository.FileStatisticRepository;
import com.javarush.matsarskaya.repository.FileUserRepository;
import com.javarush.matsarskaya.repository.StatisticRepository;
import com.javarush.matsarskaya.repository.UserRepository;
import com.javarush.matsarskaya.service.StatisticService;
import com.javarush.matsarskaya.service.UserService;

import java.util.Map;

public class HttpResolver {
    private final Map<String, Command> commandMap;

    public HttpResolver() {
        UserFileStorage storage = new UserFileStorage();
        UserRepository userRepository = new FileUserRepository(storage);
        UserService userService = new UserService(userRepository);

        StatisticRepository statisticRepository = new FileStatisticRepository();
        StatisticService statisticService = new StatisticService(statisticRepository);

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
