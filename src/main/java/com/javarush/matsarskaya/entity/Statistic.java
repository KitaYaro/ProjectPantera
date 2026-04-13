package com.javarush.matsarskaya.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.DialectOverride;

import java.time.LocalDateTime;

@Entity
@Table(name = "statistics")
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    @Column(name = "attempts")
    private int attempts = 0;
    @Column(name = "wins")
    private int wins = 0;
    @Column(name = "losses")
    private int losses = 0;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Version
    private Integer version;



    @PrePersist
    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

    public Statistic(User user, int attempts, int wins, int losses) {
        this.user = user;
        this.attempts = attempts;
        this.wins = wins;
        this.losses = losses;
    }

    public Statistic() {

    }


    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public int getAttempts() { return attempts; }
    public void setAttempts(int attempts) { this.attempts = attempts; }
    public int getWins() { return wins; }
    public void setWins(int wins) { this.wins = wins; }
    public int getLosses() { return losses; }
    public void setLosses(int losses) { this.losses = losses; }

    public void incrementAttempts() { attempts++; }
    public void incrementWins() { wins++; }
    public void incrementLosses() { losses++; }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
