package com.javarush.matsarskaya.repository;

import com.javarush.matsarskaya.entity.Statistic;

import java.util.Optional;

public interface StatisticRepository {
    Optional<Statistic> findByUsername(String username);
    void save(Statistic statistic);
}
