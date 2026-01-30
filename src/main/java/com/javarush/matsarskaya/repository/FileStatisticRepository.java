package com.javarush.matsarskaya.repository;

import com.javarush.matsarskaya.config.FileStorageConfig;
import com.javarush.matsarskaya.entity.Statistic;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileStatisticRepository implements StatisticRepository {
    private final Map<String, Statistic> statistics = new HashMap<>();
    private boolean loaded = false;

    private void loadIfNeeded() {
        if (loaded) return;
        loaded = true;

        String filePath = FileStorageConfig.getStatisticsFilePath();
        if (!Files.exists(Paths.get(filePath))) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 4) {
                    String username = parts[0];
                    int attempts = Integer.parseInt(parts[1]);
                    int wins = Integer.parseInt(parts[2]);
                    int losses = Integer.parseInt(parts[3]);

                    statistics.put(username, new Statistic(username, attempts, wins, losses));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Reading error " + filePath, e);
        }
    }

    @Override
    public Optional<Statistic> findByUsername(String username) {
        loadIfNeeded();
        return Optional.ofNullable(statistics.get(username));
    }

    @Override
    public void save(Statistic statistic) {
        loadIfNeeded();
        statistics.put(statistic.getUsername(), statistic);

        String filePath = FileStorageConfig.getStatisticsFilePath();
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Statistic s : statistics.values()) {
                writer.println(
                        s.getUsername() + ":" +
                        s.getAttempts() + ":" +
                        s.getWins() + ":" +
                        s.getLosses()
                );
            }
        } catch (IOException e) {
            throw new RuntimeException("Recording error " + filePath, e);
        }
    }
}
