package com.javarush.matsarskaya.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for the User entity")
class UserTest {

    @Test
    @DisplayName("Creating a user with correct data")
    void testUserCreation() {
        User user = new User("testuser", "password123");
        
        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getPassword()).isEqualTo("password123");
    }

    @Test
    @DisplayName("Creating a user with an empty name")
    void testUserWithEmptyUsername() {
        User user = new User("", "password123");
        
        assertThat(user.getUsername()).isEmpty();
        assertThat(user.getPassword()).isEqualTo("password123");
    }

    @Test
    @DisplayName("Creating a user with an empty password")
    void testUserWithEmptyPassword() {
        User user = new User("testuser", "");
        
        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getPassword()).isEmpty();
    }

    @Test
    @DisplayName("Getters return correct values.")
    void testGetters() {
        User user = new User("admin", "admin123");
        
        assertThat(user.getUsername()).isEqualTo("admin");
        assertThat(user.getPassword()).isEqualTo("admin123");
    }
}
