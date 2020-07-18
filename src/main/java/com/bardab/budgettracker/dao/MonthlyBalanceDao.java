package com.bardab.budgettracker.dao;

import com.bardab.budgettracker.model.Actual;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.YearMonth;

public class MonthlyBalanceDao extends AbstractDAO<Actual> {

    private SessionFactory sessionFactory;

    public MonthlyBalanceDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public Actual findByID(long id) {
        Actual actual = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            actual = (Actual) session.get(Actual.class, id);
//            session.getTransaction().commit();

        } catch (Exception e) {
            if (session.getTransaction() != null) {
                logger.info("\n ..........Transaction is being rolled back...........\n");
                session.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return actual;
    }

    public Actual findByYearMonth(YearMonth yearMonth) {
        Actual actual = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            actual = session.bySimpleNaturalId(Actual.class).load(yearMonth);
            session.getTransaction().commit();


        } catch (Exception e) {
            if (session.getTransaction() != null) {
                logger.info("\n ..........Transaction is being rolled back...........\n");
                session.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return actual;
    }




}
