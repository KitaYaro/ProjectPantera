package com.javarush.matsarskaya.service;

import com.javarush.matsarskaya.entity.Statistic;
import com.javarush.matsarskaya.entity.User;
import com.javarush.matsarskaya.repository.StatisticRepository;

import java.util.Optional;

import com.javarush.matsarskaya.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticService implements IStatisticService {
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

        Statistic statistic = new Statistic(user, 0, 0, 0);
        return statistic;
    }

    @Override
    public void registerAttempt(String username) {
        long startTime = System.currentTimeMillis();

        try {
            logger.debug("Registering an attempt for a user: {}", username);
            Statistic statistic = getOrCreate(username);
            statistic.incrementAttempts();
            statisticRepository.save(statistic);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            if (duration > 100) {
                logger.warn("registerAttempt operation took {}ms for user {}", duration, username);
            }
        }
    }

    @Override
    public void registerWin(String username) {
        long startTime = System.currentTimeMillis();

        try {
            logger.info("Victory registration for the user: {}", username);
            Statistic statistic = getOrCreate(username);
            statistic.incrementWins();
            statisticRepository.save(statistic);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            if (duration > 100) {
                logger.warn("registerWin operation took {}ms for user {}", duration, username);
            }
        }

    }

    @Override
    public void registerLoss(String username) {
        long startTime = System.currentTimeMillis();

        try {
            logger.info("Defeat registration for the user: {}", username);
            Statistic statistic = getOrCreate(username);
            statistic.incrementLosses();
            statisticRepository.save(statistic);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            if (duration > 100) {
                logger.warn("registerLoss operation took {}ms for user {}", duration, username);
            }
        }
    }

    @Override
    public Optional<Statistic> getStatistic(String username) {
        long startTime = System.currentTimeMillis();

        try {
            logger.debug("Getting statistics for the user: {}", username);
            return statisticRepository.findByUsername(username);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            if (duration > 100) {
                logger.warn("getStatistic operation took {}ms for user {}", duration, username);
            }
        }
    }
}
