package com.bardab.budgettracker.dao;
import com.bardab.budgettracker.model.BudgetForecast;
import com.bardab.budgettracker.model.MonthlyBalance;
import com.bardab.budgettracker.model.Transaction;
import com.bardab.budgettracker.model.categories.FixedCosts;
import com.bardab.budgettracker.model.categories.VariableCosts;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao extends AbstractDAO<Transaction> {

    private  SessionFactory sessionFactory;

    public final static Logger logger = Logger.getLogger(TransactionDao.class);


    public TransactionDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public SessionFactory getSessionFactory() {
        System.out.println(sessionFactory.toString());
        return this.sessionFactory;
    }

    @Override
    public Transaction findByMonthCode(int monthCode) {
        Transaction transaction=null;
        Session session = null;
        try{
            session=sessionFactory.openSession();
            session.beginTransaction();
            transaction = (Transaction) session.bySimpleNaturalId(Transaction.class).load(monthCode);

            System.out.println("Inside findById method"+transaction.getDescription());
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
        return transaction;
    }


    public  Transaction findByID(long id){
    Transaction transaction=null;
    Session session = null;
    try{
        session=sessionFactory.openSession();
        session.beginTransaction();
        transaction = (Transaction) session.bySimpleNaturalId(Transaction.class).load(20204);
        transaction = (Transaction) session.get(Transaction.class,id);

        System.out.println("Inside findById method"+transaction.getDescription());
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
    return transaction;
    }
    public void addTransactionAndUpdateAllFields(Transaction transaction) {
        Session session = null;
        MonthlyBalance monthlyBalance;

//        BudgetForecast budgetForecast=null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            monthlyBalance = session.bySimpleNaturalId(MonthlyBalance.class).load(transaction.getMonthCode());
            if(monthlyBalance == null){
                monthlyBalance = new MonthlyBalance();
                monthlyBalance.setFixedCosts(new FixedCosts());
                monthlyBalance.setVariableCosts(new VariableCosts());
                monthlyBalance.updateAllFields(transaction);
                session.save(monthlyBalance);
            }
            else monthlyBalance.updateAllFields(transaction);

            session.save(transaction);
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


}
