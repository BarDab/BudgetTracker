package com.bardab.budgettracker.dao;
import com.bardab.budgettracker.model.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

    private  SessionFactory sessionFactory;
    private static Session session;
    public final static Logger logger = Logger.getLogger(TransactionDao.class);


    public TransactionDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addTransaction(Transaction transaction){
        try{
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(transaction);
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
            if(session!=null){
                session.close();
            }
        }
    }

    public List<Transaction> displayAllTransactions(){
        List<Transaction> transactions = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            transactions.addAll(session.createQuery("FROM Transaction").list());
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
        return transactions;
    }




    public  void updateTransaction(long id, Transaction transactionUpdate){

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            transactionUpdate.setId(id);
            session.merge(transactionUpdate);
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
    }
    public  void deleteTransaction(long id){
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Transaction transaction =session.get(Transaction.class,id);
            session.delete(transaction);
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

    }

    public  Transaction findByID(long id){
    Transaction transaction=null;
    try{
        session=sessionFactory.openSession();
        session.beginTransaction();
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

    public  void deleteAllTransactions(){
       try{
           session=sessionFactory.openSession();
           session.beginTransaction();
           session.createQuery("DELETE FROM Transaction").executeUpdate();
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
    }
}
