package com.javarush.matsarskaya.entity;

import com.javarush.matsarskaya.config.FileStorageConfig;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserFileStorage {
    private final Map<String, String> users = new HashMap<>();
    private boolean loaded = false;

    public UserFileStorage() {
        loadUsersIfNeeded();
    }

    public void saveUser(String username, String password) {
        loadUsersIfNeeded();
        users.put(username, password);
        saveUsersToFile();
    }

    public Optional<String> getPasswordByUsername(String username) {
        loadUsersIfNeeded();
        return Optional.ofNullable(users.get(username));
    }

    public boolean userExists(String username) {
        loadUsersIfNeeded();
        return users.containsKey(username);
    }

    public Map<String, String> getAllUsers() {
        loadUsersIfNeeded();
        return new HashMap<>(users);
    }

    private void loadUsersIfNeeded() {
        if (!loaded) {
            loadUsersFromFile();
            loaded = true;
        }
    }

    private void saveUsersToFile() {
        String filePath = FileStorageConfig.getUsersFilePath();
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                writer.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Error when writing to a file: " + e.getMessage());
        }
    }

    private void loadUsersFromFile() {
        String filePath = FileStorageConfig.getUsersFilePath();
        if (!Files.exists(Paths.get(filePath))) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error when reading from a file: " + e.getMessage());
        }
    }
}
