package com.bardab.budgettracker.dao;

import com.bardab.budgettracker.model.MonthlyBalance;
import com.bardab.budgettracker.model.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MonthlyBalanceDao extends AbstractDAO<MonthlyBalance> {

    private SessionFactory sessionFactory;

    public MonthlyBalanceDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public MonthlyBalance findByID(long id){
        MonthlyBalance monthlyBalance =null;
        Session session = null;
        try{
            session=sessionFactory.openSession();
            session.beginTransaction();
            monthlyBalance = (MonthlyBalance) session.get(MonthlyBalance.class,id);

        }    catch (Exception e){
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
        return monthlyBalance;
    }
}
