package com.javarush.matsarskaya.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for the Statistical entity")
class StatisticTest {

    private Statistic statistic;

    @BeforeEach
    void setUp() {
        statistic = new Statistic("testuser", 0, 0, 0);
    }

    @Test
    @DisplayName("Creating statistics with zero values")
    void testStatisticCreationWithZeros() {
        assertThat(statistic.getUsername()).isEqualTo("testuser");
        assertThat(statistic.getAttempts()).isEqualTo(0);
        assertThat(statistic.getWins()).isEqualTo(0);
        assertThat(statistic.getLosses()).isEqualTo(0);
    }

    @Test
    @DisplayName("Creating statistics with initial values")
    void testStatisticCreationWithValues() {
        Statistic stat = new Statistic("player1", 10, 5, 5);
        
        assertThat(stat.getUsername()).isEqualTo("player1");
        assertThat(stat.getAttempts()).isEqualTo(10);
        assertThat(stat.getWins()).isEqualTo(5);
        assertThat(stat.getLosses()).isEqualTo(5);
    }

    @Test
    @DisplayName("Increasing the number of attempts")
    void testIncrementAttempts() {
        statistic.incrementAttempts();
        assertThat(statistic.getAttempts()).isEqualTo(1);
        
        statistic.incrementAttempts();
        statistic.incrementAttempts();
        assertThat(statistic.getAttempts()).isEqualTo(3);
    }

    @Test
    @DisplayName("Increasing the number of wins")
    void testIncrementWins() {
        statistic.incrementWins();
        assertThat(statistic.getWins()).isEqualTo(1);
        
        statistic.incrementWins();
        statistic.incrementWins();
        assertThat(statistic.getWins()).isEqualTo(3);
    }

    @Test
    @DisplayName("An increase in the number of lesions")
    void testIncrementLosses() {
        statistic.incrementLosses();
        assertThat(statistic.getLosses()).isEqualTo(1);
        
        statistic.incrementLosses();
        statistic.incrementLosses();
        assertThat(statistic.getLosses()).isEqualTo(3);
    }

    @Test
    @DisplayName("Getters return correct values.")
    void testGetters() {
        Statistic stat = new Statistic("user2", 15, 8, 7);
        
        assertThat(stat.getUsername()).isEqualTo("user2");
        assertThat(stat.getAttempts()).isEqualTo(15);
        assertThat(stat.getWins()).isEqualTo(8);
        assertThat(stat.getLosses()).isEqualTo(7);
    }
}
