package com.javarush.matsarskaya.service;

import com.javarush.matsarskaya.entity.Statistic;
import com.javarush.matsarskaya.entity.User;
import com.javarush.matsarskaya.repository.StatisticRepository;
import java.util.Optional;
import com.javarush.matsarskaya.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticService {
    private static final Logger logger = LoggerFactory.getLogger(StatisticService.class);
    private final StatisticRepository statisticRepository;
    private final UserRepository userRepository;

    public StatisticService(StatisticRepository statisticRepository, UserRepository userRepository) {
        this.statisticRepository = statisticRepository;
        this.userRepository = userRepository;
        logger.info("StatisticService initialized");
    }

    private Statistic getOrCreate(String username) {
        logger.debug("Getting or creating statistics for a user: {}", username);

        Optional<Statistic> existing = statisticRepository.findByUsername(username);
        if (existing.isPresent()) {
            return existing.get();
        }

        Optional<User> userOpt = userRepository.findByUsername(username);
        User user = userOpt.orElseThrow(() -> new RuntimeException("User not found" + username));

        Statistic statistic = new Statistic(user, 0,0,0);
        return statistic;
    }

    public void registerAttempt(String username) {
        logger.debug("Registering an attempt for a user: {}", username);
        Statistic statistic = getOrCreate(username);
        statistic.incrementAttempts();
        statisticRepository.save(statistic);
    }

    public void registerWin(String username) {
        logger.info("Victory registration for the user: {}", username);
        Statistic statistic = getOrCreate(username);
        statistic.incrementWins();
        statisticRepository.save(statistic);
    }

    public void registerLoss(String username) {
        logger.info("Defeat registration for the user: {}", username);
        Statistic statistic = getOrCreate(username);
        statistic.incrementLosses();
        statisticRepository.save(statistic);
    }

    public Optional<Statistic> getStatistic(String username) {
        logger.debug("Getting statistics for the user: {}", username);
        return statisticRepository.findByUsername(username);
    }
}
