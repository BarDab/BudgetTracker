package com.bardab.budgettracker.dao;

import com.bardab.budgettracker.model.BudgetForecast;
import com.bardab.budgettracker.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class BudgetForecastDao extends AbstractDAO <BudgetForecast> {

    private SessionFactory sessionFactory;

    public BudgetForecastDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Override
    public BudgetForecast findByID(long id) {
        BudgetForecast budgetForecast=null;
        Session session=null;
        try{
            session = getSessionFactory().openSession();
            session.beginTransaction();
            budgetForecast = session.get(BudgetForecast.class,id);
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
}
