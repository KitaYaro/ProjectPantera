package com.javarush.matsarskaya.entity;

public class Statistic {
    private final String username;
    private int attempts;
    private int wins;
    private int losses;

    public Statistic(String username, int attempts, int wins, int losses) {
        this.username = username;
        this.attempts = attempts;
        this.wins = wins;
        this.losses = losses;
    }

    public String getUsername() {
        return username;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public void incrementAttempts() {
        attempts++;
    }

    public void incrementWins() {
        wins++;
    }

    public void incrementLosses() {
        losses++;
    }
}
