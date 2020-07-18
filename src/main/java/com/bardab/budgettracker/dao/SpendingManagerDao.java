package com.bardab.budgettracker.dao;

import com.bardab.budgettracker.model.SpendingManager;
import com.bardab.budgettracker.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.YearMonth;

public class SpendingManagerDao extends AbstractDAO<SpendingManager> {

    private SessionFactory sessionFactory;

    public SpendingManagerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public SessionFactory getSessionFactory() {
        return HibernateUtil.getInstance().getSessionFactory();
    }

    @Override
    public SpendingManager findByID(long id) {
        return null;
    }

    @Override
    public SpendingManager findByYearMonth(YearMonth yearMonth) {
        SpendingManager spendingManager = null;
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            spendingManager = session.bySimpleNaturalId(SpendingManager.class).load(yearMonth);
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
        return spendingManager;
    }
}
