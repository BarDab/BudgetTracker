package com.bardab.budgettracker.dao.categories;

import com.bardab.budgettracker.dao.AbstractDAO;
import com.bardab.budgettracker.model.categories.PlannedExpenses;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;


public class PlannedExpensesDao extends AbstractDAO<PlannedExpenses> {
    private SessionFactory sessionFactory;

    public PlannedExpensesDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public PlannedExpenses findByMonthCode(int monthCode) {
        PlannedExpenses plannedExpenses =null;
        Session session=null;
        try{
            session = getSessionFactory().openSession();
            session.beginTransaction();
            plannedExpenses = session.bySimpleNaturalId(PlannedExpenses.class).load(monthCode);
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
        return plannedExpenses;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Override
    public PlannedExpenses findByID(long id) {
        PlannedExpenses plannedExpenses =null;
        Session session=null;
        try{
            session = getSessionFactory().openSession();
            session.beginTransaction();
            plannedExpenses = session.get(PlannedExpenses.class,id);
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
        return plannedExpenses;
    }

    public PlannedExpenses getLastFixedCostsRecord(){
        PlannedExpenses plannedExpenses =null;
        Session session=null;
        try{
            session = getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from PlannedExpenses order by budget_ID desc");
            query.setMaxResults(1);
            plannedExpenses = (PlannedExpenses) query.getSingleResult();
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


        return plannedExpenses;
    }
}


