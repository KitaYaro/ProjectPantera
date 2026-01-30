package com.javarush.matsarskaya.repository;

import com.javarush.matsarskaya.entity.User;
import com.javarush.matsarskaya.entity.UserFileStorage;
import com.javarush.matsarskaya.exception.UserAlreadyExistsException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUserRepository implements UserRepository{
    private static final Logger logger = LoggerFactory.getLogger(FileUserRepository.class);
    private final UserFileStorage storage;

    public FileUserRepository(UserFileStorage storage) {
        this.storage = storage;
        logger.info("FileUserRepository initialized");
    }

    @Override
    public boolean save(User user) {
        logger.debug("Saving the user: {}", user.getUsername());
        if (storage.userExists(user.getUsername())) {
            logger.warn("The user {} already exists in the repository", user.getUsername());
            throw new UserAlreadyExistsException(user.getUsername());
        }
        storage.saveUser(user.getUsername(), user.getPassword());
        logger.info("User {} saved successfully", user.getUsername());
        return true;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        logger.debug("Search for a user by name: {}", username);
        Optional<String> password = storage.getPasswordByUsername(username);
        if (password.isPresent()) {
            logger.debug("User {} found", username);
        } else {
            logger.debug("User {} not found", username);
        }
        return password.map(pwd -> new User(username, pwd));
    }

    @Override
    public boolean existsByUsername(String username) {
        logger.debug("Verifying the user's existence: {}", username);
        return storage.userExists(username);
    }
}
