package com.bardab.budgettracker.dao;

import com.bardab.budgettracker.model.Budget;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.YearMonth;

public class BudgetDao extends AbstractDAO <Budget> {

    private SessionFactory sessionFactory;

    public BudgetDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
    }


    public Budget findByYearMonth(YearMonth yearMonth) {
        Budget budget =null;
        Session session=null;
        try{
            session = getSessionFactory().openSession();
            session.beginTransaction();
            budget = session.bySimpleNaturalId(Budget.class).load(yearMonth);
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
        return budget;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Override
    public Budget findByID(long id) {
        Budget budget =null;
        Session session=null;
        try{
            session = getSessionFactory().openSession();
            session.beginTransaction();
            budget = session.get(Budget.class,id);
//            session.getTransaction().commit();
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
        return budget;
    }
}
