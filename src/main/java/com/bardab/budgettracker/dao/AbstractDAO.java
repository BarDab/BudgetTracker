package com.bardab.budgettracker.dao;

import com.bardab.budgettracker.model.Transaction;
import com.bardab.budgettracker.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.jboss.logging.Logger;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T> {

    // needs change
    public final static Logger logger = Logger.getLogger(TransactionDao.class);


    public abstract SessionFactory getSessionFactory();

    public abstract T findByID(long id);

    public abstract T findByMonthCode(int monthCode);


    public void addEntity(T instanceOfAnnotatedClass) {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            session.save(instanceOfAnnotatedClass);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
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
    }

    public List<T> displayAllTransactions(String tableName) {
        List<T> instancesOfAnnotatedClass = new ArrayList<>();
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            instancesOfAnnotatedClass.addAll(session.createQuery("FROM " + tableName).list());
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
        return instancesOfAnnotatedClass;
    }

    public void updateTransaction(long id, T instanceOfAnnotatedClass) {

        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            session.merge(instanceOfAnnotatedClass);
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
    }


    public void deleteTransaction(long id) {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(findByID(id));
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

    }

    public void deleteAllTransactions(String tableName) {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            session.createQuery("DELETE FROM " + tableName).executeUpdate();
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
    }

}
