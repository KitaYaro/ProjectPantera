package com.javarush.matsarskaya.config;

public class ApplicationConstants {

    //URL paths
    public static final String PATH_HOME = "/home-page";
    public static final String PATH_LOGIN = "/login-page";
    public static final String PATH_REGISTER = "/register-page";
    public static final String PATH_QUEST_DRAGON = "/quest-dragon";
    public static final String PATH_LOGOUT = "/logout";
    public static final String PATH_STATISTIC = "/statistic-page";

    // Session attributes
    public static final String SESSION_USERNAME = "username";
    public static final String SESSION_USER = "user";

    // Request parameters
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_ACTION = "action";

    // Error messages
    public static final String ERROR_USER_NOT_FOUND = "User not found";
    public static final String ERROR_INVALID_CREDENTIALS = "Invalid username or password";
    public static final String ERROR_USER_ALREADY_EXISTS = "User already exists";
    public static final String ERROR_UNEXPECTED = "An unexpected error occurred";
    public static final String ERROR_LOGGING_IN = "Error occurred when logging in";

    // Success messages
    public static final String MSG_LOGIN_SUCCESS = "Login successful";
    public static final String MSG_REGISTRATION_SUCCESS = "Registration successful";
    public static final String MSG_LOGOUT_SUCCESS = "Logout successful";

    // View paths (JSP)
    public static final String VIEW_HOME = "/WEB-INF/home-page.jsp";
    public static final String VIEW_LOGIN = "/WEB-INF/login-page.jsp";
    public static final String VIEW_REGISTER = "/WEB-INF/register-page.jsp";
    public static final String VIEW_QUEST_DRAGON = "/WEB-INF/quest-dragon.jsp";
    public static final String VIEW_STATISTIC = "/WEB-INF/statistic-page.jsp";

    // Quest constants
    public static final int QUEST_INITIAL_TRUST = 50;
    public static final int QUEST_DEFAULT_TRUST = 50;
    public static final int QUEST_LOSS_THRESHOLD_EARLY = 50;
    public static final int QUEST_LOSS_THRESHOLD_LATE = 70;
    public static final int QUEST_WIN_THRESHOLD = 70;
    public static final String QUEST_NAME = "the way of the dragon rider";

    // Session attributes for Quest
    public static final String SESSION_ATTR_TRUST = "trust";
    public static final String SESSION_ATTR_STAGE = "stage";
    public static final String SESSION_ATTR_QUEST_FINISHED = "questFinished";
    public static final String SESSION_ATTR_PLAYER_NAME = "playerName";

    // Request parameters for Quest
    public static final String PARAM_STAGE = "stage";
    public static final String PARAM_CHOICE = "choice";
    public static final String PARAM_PLAYER_NAME = "playerNameInput";
    public static final String PARAM_QUEST = "quest";

    // Protected paths (array for easier checking)
    public static final String[] PROTECTED_PATHS = {
            PATH_QUEST_DRAGON,
            PATH_STATISTIC
    };

    private ApplicationConstants(){
    }
}
