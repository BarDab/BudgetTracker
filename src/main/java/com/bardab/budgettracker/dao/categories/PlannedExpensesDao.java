package com.bardab.budgettracker.dao.categories;

import com.bardab.budgettracker.dao.AbstractDAO;
import com.bardab.budgettracker.model.BudgetExpenses;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.time.YearMonth;


public class PlannedExpensesDao extends AbstractDAO<BudgetExpenses> {
    private SessionFactory sessionFactory;

    public PlannedExpensesDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public BudgetExpenses findByYearMonth(YearMonth yearMonth) {
        BudgetExpenses budgetExpenses =null;
        Session session=null;
        try{
            session = getSessionFactory().openSession();
            session.beginTransaction();
            budgetExpenses = session.bySimpleNaturalId(BudgetExpenses.class).load(yearMonth);
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
        return budgetExpenses;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Override
    public BudgetExpenses findByID(long id) {
        BudgetExpenses budgetExpenses =null;
        Session session=null;
        try{
            session = getSessionFactory().openSession();
            session.beginTransaction();
            budgetExpenses = session.get(BudgetExpenses.class,id);
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
        return budgetExpenses;
    }

    public BudgetExpenses getLastFixedCostsRecord(){
        BudgetExpenses budgetExpenses =null;
        Session session=null;
        try{
            session = getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from PlannedExpenses order by budget_ID desc");
            query.setMaxResults(1);
            budgetExpenses = (BudgetExpenses) query.getSingleResult();
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


        return budgetExpenses;
    }
}


