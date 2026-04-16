package com.javarush.matsarskaya.util;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    static {
        try{
            Class.forName("org.postgresql.Driver");
            Configuration configuration = new Configuration().configure("META-INF/hibernate.cfg.xml");

            configuration.addAnnotatedClass(com.javarush.matsarskaya.entity.User.class);
            configuration.addAnnotatedClass(com.javarush.matsarskaya.entity.Statistic.class);

            String dbUrl = System.getProperty("DB_URL", "jdbc:postgresql://localhost:5433/quest");
            String dbUser = System.getProperty("DB_USER", "postgres");
            String dbPassword = System.getProperty("DB_PASSWORD", "postgres");

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

    private static void shotdown(){
        if(sessionFactory != null && !sessionFactory.isClosed()){
            sessionFactory.close();
        }
    }
}
