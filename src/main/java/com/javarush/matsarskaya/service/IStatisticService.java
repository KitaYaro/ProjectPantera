package com.javarush.matsarskaya.service;

import com.javarush.matsarskaya.dto.StatisticDTO;
import com.javarush.matsarskaya.entity.Statistic;

import java.util.Optional;

public interface IStatisticService {
    void registerAttempt(String username);
    void registerWin(String username);
    void registerLoss(String username);
    Optional<Statistic> getStatistic(String username);
}
