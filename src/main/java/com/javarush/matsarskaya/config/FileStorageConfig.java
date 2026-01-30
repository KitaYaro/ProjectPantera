package com.javarush.matsarskaya.config;

public class FileStorageConfig {
    private static final String USERS_FILE_PATH = "users.txt";
    private static final String STATISTICS_FILE_PATH = "statistics.txt";

    private FileStorageConfig() {
    }
    public static String getUsersFilePath() {
        return USERS_FILE_PATH;
    }
    public static String getStatisticsFilePath() {
        return STATISTICS_FILE_PATH;
    }
}
