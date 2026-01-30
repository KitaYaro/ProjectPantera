package com.javarush.matsarskaya.repository;

import com.javarush.matsarskaya.entity.User;
import com.javarush.matsarskaya.entity.UserFileStorage;
import com.javarush.matsarskaya.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for FileUserRepository")
class FileUserRepositoryTest {
    @Mock
    private UserFileStorage storage;

    private FileUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new FileUserRepository(storage);
    }

    @Test
    @DisplayName("Successful saving of a new user")
    void testSaveNewUser() {
        when(storage.userExists("newuser")).thenReturn(false);

        User user = new User("newuser", "password123");
        boolean result = repository.save(user);

        assertThat(result).isTrue();
        verify(storage).userExists("newuser");
        verify(storage).saveUser("newuser", "password123");
    }

    @Test
    @DisplayName("Saving an existing user throws an exception")
    void testSaveExistingUser() {
        when(storage.userExists("existinguser")).thenReturn(true);

        User user = new User("existinguser", "password123");

        assertThatThrownBy(() -> repository.save(user))
            .isInstanceOf(UserAlreadyExistsException.class)
            .hasMessageContaining("username");

        verify(storage).userExists("existinguser");
        verify(storage, never()).saveUser(anyString(), anyString());
    }

    @Test
    @DisplayName("Search for an existing user by name")
    void testFindByUsernameExistingUser() {
        when(storage.getPasswordByUsername("testuser")).thenReturn(Optional.of("password123"));

        Optional<User> result = repository.findByUsername("testuser");

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("testuser");
        assertThat(result.get().getPassword()).isEqualTo("password123");
        verify(storage).getPasswordByUsername("testuser");
    }

    @Test
    @DisplayName("Search for a non-existent user by name")
    void testFindByUsernameNonExistingUser() {
        when(storage.getPasswordByUsername("nonexistent")).thenReturn(Optional.empty());

        Optional<User> result = repository.findByUsername("nonexistent");

        assertThat(result).isEmpty();
        verify(storage).getPasswordByUsername("nonexistent");
    }

    @Test
    @DisplayName("Verification of user's existence - user exists")
    void testExistsByUsernameTrue() {
        when(storage.userExists("existinguser")).thenReturn(true);

        boolean result = repository.existsByUsername("existinguser");

        assertThat(result).isTrue();
        verify(storage).userExists("existinguser");
    }

    @Test
    @DisplayName("Verification of user's existence - user does not exist")
    void testExistsByUsernameFalse() {
        when(storage.userExists("nonexistent")).thenReturn(false);

        boolean result = repository.existsByUsername("nonexistent");

        assertThat(result).isFalse();
        verify(storage).userExists("nonexistent");
    }
}
