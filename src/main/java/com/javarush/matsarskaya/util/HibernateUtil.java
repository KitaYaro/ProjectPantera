package com.javarush.matsarskaya.util;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class HibernateUtil {
    // Синглтон - инстанс
    private static final SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    static {
        try{
            // Явно загружаем JDBC драйвер PostgreSQL
            Class.forName("org.postgresql.Driver");
            
            // Загружаем конфигурацию из hibernate.cfg.xml
            Configuration configuration = new Configuration().configure("META-INF/hibernate.cfg.xml");
            
            // Явно добавляем маппинги сущностей
            configuration.addAnnotatedClass(com.javarush.matsarskaya.entity.User.class);
            configuration.addAnnotatedClass(com.javarush.matsarskaya.entity.Statistic.class);

            // Читаем переменные окружения для подключения к БД
            // При запуске в Docker переменные передаются через -D аргументы
            // При локальном запуске используются значения по умолчанию
            String dbUrl = System.getProperty("DB_URL", "jdbc:postgresql://localhost:5433/quest");
            String dbUser = System.getProperty("DB_USER", "postgres");
            String dbPassword = System.getProperty("DB_PASSWORD", "postgres");

            // Переопределяем параметры подключения
            configuration.setProperty("hibernate.connection.url", dbUrl);
            configuration.setProperty("hibernate.connection.username", dbUser);
            configuration.setProperty("hibernate.connection.password", dbPassword);

            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(registryBuilder.build());


        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    // Метод для закрытия sessionFactory при завершении работы
    private static void shotdown(){
        if(sessionFactory != null && !sessionFactory.isClosed()){
            sessionFactory.close();
        }
    }
}
