# ProjectPantera

## Описание

ProjectPantera - это учебный веб-проект на Java Servlets, демонстрирующий веб-приложение с функциональностью регистрации, авторизации, игровой механикой квеста и отслеживанием статистики.

## Технологии

- Java 21
- Jakarta Servlet API
- JSP (JavaServer Pages)
- Hibernate ORM
- PostgreSQL
- Maven
- JUnit 5
- Mockito
- Lombok
- Logback

## Структура проекта

```
src/main/java/
├── com/javarush/matsarskaya/
│   ├── cmd/                  # Реализации команд (страниц)
│   ├── controller/           # Контроллеры (FrontController, HttpResolver)
│   ├── config/               # Константы приложения
│   ├── dto/                  # Объекты передачи данных
│   ├── entity/               # Сущности Hibernate
│   ├── exception/            # Кастомные исключения
│   ├── repository/           # Репозитории доступа к данным
│   ├── service/              # Бизнес-логика сервисов
│   └── util/                 # Утилитные классы
src/main/webapp/              # Веб-ресурсы (JSP, CSS, изображения)
├── WEB-INF/
│   ├── jsp/                  # JSP-страницы
│   │   ├── home-page.jsp
│   │   ├── login-page.jsp
│   │   ├── register-page.jsp
│   │   ├── quest-dragon.jsp
│   │   └── statistic-page.jsp
│   ├── header.jsp            # Общий хедер для страниц
│   └── web.xml               # Деплоймент дескриптор
├── css/                      # Стили CSS
└── images/                   # Изображения
src/main/resources/           # Конфигурации (Hibernate, Liquibase, Logback)
src/test/java/                # Тесты
└── com/javarush/matsarskaya/
    ├── cmd/                  # Тесты команд
    ├── controller/           # Тесты контроллеров
    ├── exception/            # Тесты исключений
    ├── entity/               # Тесты сущностей
    ├── repository/           # Тесты репозиториев
    └── service/              # Тесты сервисов
```

## Основные особенности

### Архитектура
- Применен паттерн Front Controller для централизованной обработки запросов
- Используется Dependency Injection для слабой связанности компонентов
- Разделение ответственностей: контроллеры, сервисы, репозитории, сущности
- Утилитные классы для повторно используемой функциональности
- DAO pattern для абстракции доступа к данным

### Функциональность
- Регистрация и авторизация пользователей
- Хеширование паролей для безопасности
- Игровой квест "Путь дракона" с системой доверия и этапами
- Отслеживание и отображение статистики пользователей
- Защищенные маршруты requiring authentication



