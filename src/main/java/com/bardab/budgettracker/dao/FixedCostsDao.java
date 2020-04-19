package com.bardab.budgettracker.dao;

import com.bardab.budgettracker.model.FixedCosts;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FixedCostsDao extends AbstractDAO<FixedCosts> {
    private SessionFactory sessionFactory;

    public FixedCostsDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Override
    public FixedCosts findByID(long id) {
        FixedCosts budgetForecast=null;
        Session session=null;
        try{
            session = getSessionFactory().openSession();
            session.beginTransaction();
            budgetForecast = session.get(FixedCosts.class,id);
            session.getTransaction().commit();
        }
        catch (Exception e){
            if(session.getTransaction()!=null){
                logger.info("\n ..........Transaction is being rolled back...........\n");
                session.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
        return budgetForecast;
    }

    public FixedCosts getLastFixedCostsRecord(){
        FixedCosts fixedCosts =null;
        Session session=null;
        try{
            session = getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from FixedCosts order by budgetForecast_ID desc");
            query.setMaxResults(1);
            fixedCosts = (FixedCosts) query.getSingleResult();
            session.getTransaction().commit();
        }
        catch (Exception e){
            if(session.getTransaction()!=null){
                logger.info("\n ..........Transaction is being rolled back...........\n");
                session.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }
        finally {
            if (session != null) {
                session.close();
            }
        }

        fixedCosts.setId(null);
        return fixedCosts;
    }
}


