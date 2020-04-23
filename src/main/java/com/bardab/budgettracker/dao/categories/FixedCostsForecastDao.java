package com.bardab.budgettracker.dao.categories;

import com.bardab.budgettracker.dao.AbstractDAO;
import com.bardab.budgettracker.model.categories.FixedCostsForecast;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;


public class FixedCostsForecastDao extends AbstractDAO<FixedCostsForecast> {
    private SessionFactory sessionFactory;

    public FixedCostsForecastDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Override
    public FixedCostsForecast findByID(long id) {
        FixedCostsForecast fixedCostsForecast =null;
        Session session=null;
        try{
            session = getSessionFactory().openSession();
            session.beginTransaction();
            fixedCostsForecast = session.get(FixedCostsForecast.class,id);
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
        return fixedCostsForecast;
    }

    public FixedCostsForecast getLastFixedCostsRecord(){
        FixedCostsForecast fixedCostsForecast =null;
        Session session=null;
        try{
            session = getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from FixedCostsForecast order by budgetForecast_ID desc");
            query.setMaxResults(1);
            fixedCostsForecast = (FixedCostsForecast) query.getSingleResult();
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

        fixedCostsForecast.setId(null);
        return fixedCostsForecast;
    }
}


