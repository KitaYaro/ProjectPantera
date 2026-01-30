package com.javarush.matsarskaya.service;

import com.javarush.matsarskaya.entity.Statistic;
import com.javarush.matsarskaya.repository.StatisticRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticService {
    private static final Logger logger = LoggerFactory.getLogger(StatisticService.class);
    private final StatisticRepository repository;

    public StatisticService(StatisticRepository repository) {
        this.repository = repository;
        logger.info("StatisticService initialized");
    }

    private Statistic getOrCreate(String username) {
        logger.debug("Getting or creating statistics for a user: {}", username);
        return repository.findByUsername(username)
                .orElseGet(() -> new Statistic(username, 0, 0, 0));
    }

    public void registerAttempt(String username) {
        logger.debug("Registering an attempt for a user: {}", username);
        Statistic statistic = getOrCreate(username);
        statistic.incrementAttempts();
        repository.save(statistic);
    }

    public void registerWin(String username) {
        logger.info("Victory registration for the user: {}", username);
        Statistic statistic = getOrCreate(username);
        statistic.incrementWins();
        repository.save(statistic);
    }

    public void registerLoss(String username) {
        logger.info("Defeat registration for the user: {}", username);
        Statistic statistic = getOrCreate(username);
        statistic.incrementLosses();
        repository.save(statistic);
    }

    public Optional<Statistic> getStatistic(String username) {
        logger.debug("Getting statistics for the user: {}", username);
        return repository.findByUsername(username);
    }
}
