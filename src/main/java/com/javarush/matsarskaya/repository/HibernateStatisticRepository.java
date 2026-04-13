package com.javarush.matsarskaya.repository;

import com.javarush.matsarskaya.entity.Statistic;
import com.javarush.matsarskaya.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.Optional;

public class HibernateStatisticRepository implements StatisticRepository {
    private final SessionFactory sessionFactory;

    public HibernateStatisticRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Statistic> findByUsername (String username){
        try (Session session = sessionFactory.openSession()) {
            Query<Statistic> query = session.createQuery("from Statistic where user.username = :username", Statistic.class);
            query.setParameter("username", username);
            return query.uniqueResultOptional();
        }
    }

    @Override
    public void save(Statistic statistic) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            // Ищем существующую запись
            Statistic existing = session.createQuery(
                            "from Statistic where user.username = :username", Statistic.class)
                    .setParameter("username", statistic.getUser().getUsername())
                    .uniqueResult();

            if (existing != null) {
                // Используем значения из переданного объекта (которые уже были инкрементированы)
                existing.setAttempts(statistic.getAttempts());
                existing.setWins(statistic.getWins());
                existing.setLosses(statistic.getLosses());
                session.merge(existing);
            } else {
                session.persist(statistic);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.getStatus().canRollback()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void save (Statistic statistic){
//        Transaction tx = null;
//        try (Session session = sessionFactory.openSession()) {
//           tx = session.beginTransaction();
//           Query<Statistic> query = session.createQuery("from Statistic where user.username = :username", Statistic.class);
//          query.setParameter("username", statistic.getUser().getUsername());
//            Optional<Statistic> existing = query.uniqueResultOptional();
//            // Пытаемся найти существующую запись
//            Statistic existing = session.createQuery("from Statistic where user.username = :username", Statistic.class)
//                    .setParameter("username", statistic.getUser().getUsername())
//                    .uniqueResult();
//
//            if (existing != null) {
//              // Копируем значения из переданного объекта в существующий
//            existing.setAttempts(statistic.getAttempts());
//            existing.setWins(statistic.getWins());
//            existing.setLosses(statistic.getLosses());
//            // Объект existing уже находится в состоянии persistent, изменения сохранятся при коммите
//            } else {
//                session.persist(statistic);
//            }
//            tx.commit();
//        } catch (Exception e) {
//            if (tx != null && tx.getStatus().canRollback()) {
//                tx.rollback();
//                throw new RuntimeException(e);
//            }
//        }
//    }
}
