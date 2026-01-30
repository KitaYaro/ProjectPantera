package com.javarush.matsarskaya.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for FileStorageConfig")
class FileStorageConfigTest {

    @Test
    @DisplayName("Getting the path to the user's file")
    void testGetUsersFilePath() {
        String path = FileStorageConfig.getUsersFilePath();

        assertThat(path).isEqualTo("users.txt");
    }

    @Test
    @DisplayName("Getting the path to the statistics file")
    void testGetStatisticsFilePath() {
        String path = FileStorageConfig.getStatisticsFilePath();

        assertThat(path).isEqualTo("statistics.txt");
    }

    @Test
    @DisplayName("Paths are not null")
    void testPathsAreNotNull() {
        assertThat(FileStorageConfig.getUsersFilePath()).isNotNull();
        assertThat(FileStorageConfig.getStatisticsFilePath()).isNotNull();
    }

    @Test
    @DisplayName("Paths are not empty")
    void testPathsAreNotEmpty() {
        assertThat(FileStorageConfig.getUsersFilePath()).isNotEmpty();
        assertThat(FileStorageConfig.getStatisticsFilePath()).isNotEmpty();
    }
}
