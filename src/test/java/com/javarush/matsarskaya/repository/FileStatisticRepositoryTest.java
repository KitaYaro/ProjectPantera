package com.javarush.matsarskaya.repository;

import com.javarush.matsarskaya.entity.Statistic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for FileStatisticRepository")
class FileStatisticRepositoryTest {

    @TempDir
    Path tempDir;

    private FileStatisticRepository repository;
    private String testFilePath;

    @Test
    @DisplayName("Saving and uploading statistics")
    void testSaveAndLoadStatistic() throws IOException {
        testFilePath = tempDir.resolve("test_statistics.txt").toString();
        System.setProperty("statistics.file.path", testFilePath);
        
        repository = new FileStatisticRepository();
        
        Statistic stat = new Statistic("testuser", 10, 5, 5);
        repository.save(stat);

        Optional<Statistic> loaded = repository.findByUsername("testuser");

        assertThat(loaded).isPresent();
        assertThat(loaded.get().getUsername()).isEqualTo("testuser");
        assertThat(loaded.get().getAttempts()).isEqualTo(10);
        assertThat(loaded.get().getWins()).isEqualTo(5);
        assertThat(loaded.get().getLosses()).isEqualTo(5);
    }

    @Test
    @DisplayName("Search for non-existent statistics")
    void testFindNonExistingStatistic() throws IOException {
        testFilePath = tempDir.resolve("test_statistics.txt").toString();
        System.setProperty("statistics.file.path", testFilePath);
        
        repository = new FileStatisticRepository();

        Optional<Statistic> result = repository.findByUsername("nonexistent");

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Updating existing statistics")
    void testUpdateExistingStatistic() throws IOException {
        testFilePath = tempDir.resolve("test_statistics.txt").toString();
        System.setProperty("statistics.file.path", testFilePath);
        
        repository = new FileStatisticRepository();
        
        Statistic stat1 = new Statistic("testuser", 5, 2, 3);
        repository.save(stat1);

        Statistic stat2 = new Statistic("testuser", 10, 6, 4);
        repository.save(stat2);

        Optional<Statistic> loaded = repository.findByUsername("testuser");

        assertThat(loaded).isPresent();
        assertThat(loaded.get().getAttempts()).isEqualTo(10);
        assertThat(loaded.get().getWins()).isEqualTo(6);
        assertThat(loaded.get().getLosses()).isEqualTo(4);
    }

    @Test
    @DisplayName("Saving statistics for multiple users")
    void testSaveMultipleStatistics() throws IOException {
        testFilePath = tempDir.resolve("test_statistics.txt").toString();
        System.setProperty("statistics.file.path", testFilePath);
        
        repository = new FileStatisticRepository();
        
        repository.save(new Statistic("user1", 10, 5, 5));
        repository.save(new Statistic("user2", 15, 8, 7));
        repository.save(new Statistic("user3", 20, 12, 8));

        Optional<Statistic> stat1 = repository.findByUsername("user1");
        Optional<Statistic> stat2 = repository.findByUsername("user2");
        Optional<Statistic> stat3 = repository.findByUsername("user3");

        assertThat(stat1).isPresent();
        assertThat(stat2).isPresent();
        assertThat(stat3).isPresent();

        assertThat(stat1.get().getAttempts()).isEqualTo(10);
        assertThat(stat2.get().getAttempts()).isEqualTo(15);
        assertThat(stat3.get().getAttempts()).isEqualTo(20);
    }
}
