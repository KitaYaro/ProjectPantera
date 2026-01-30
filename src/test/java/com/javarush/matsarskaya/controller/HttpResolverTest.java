package com.javarush.matsarskaya.controller;

import com.javarush.matsarskaya.cmd.Command;
import com.javarush.matsarskaya.cmd.HomePage;
import com.javarush.matsarskaya.cmd.LoginPage;
import com.javarush.matsarskaya.cmd.QuestDragon;
import com.javarush.matsarskaya.cmd.RegisterPage;
import com.javarush.matsarskaya.cmd.StatisticPage;
import com.javarush.matsarskaya.cmd.LogoutPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for HttpResolver")
class HttpResolverTest {
    private HttpResolver httpResolver;

    @BeforeEach
    void setUp() {
        httpResolver = new HttpResolver();
    }

    @Test
    @DisplayName("Team permission for the home page")
    void testResolveHomePage() {
        Command command = httpResolver.resolve("/home-page");

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(HomePage.class);
    }

    @Test
    @DisplayName("Team permission for the quest page")
    void testResolveQuestDragon() {
        Command command = httpResolver.resolve("/quest-dragon");

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(QuestDragon.class);
    }

    @Test
    @DisplayName("Team permission for the login page")
    void testResolveLoginPage() {
        Command command = httpResolver.resolve("/login-page");

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(LoginPage.class);
    }

    @Test
    @DisplayName("Team permission for the registration page")
    void testResolveRegisterPage() {
        Command command = httpResolver.resolve("/register-page");

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(RegisterPage.class);
    }

    @Test
    @DisplayName("Allowing the command to exit")
    void testResolveLogout() {
        Command command = httpResolver.resolve("/logout");

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(LogoutPage.class);
    }

    @Test
    @DisplayName("Team permission for the statistics page")
    void testResolveStatisticPage() {
        Command command = httpResolver.resolve("/statistic-page");

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(StatisticPage.class);
    }

    @Test
    @DisplayName("Resolving an unknown path returns the home page")
    void testResolveUnknownPath() {
        Command command = httpResolver.resolve("/unknown-path");

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(HomePage.class);
    }
}
