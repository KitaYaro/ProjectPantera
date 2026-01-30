package com.javarush.matsarskaya.repository;

import com.javarush.matsarskaya.entity.User;

import java.util.Optional;

public interface UserRepository {
    boolean save(User user);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
