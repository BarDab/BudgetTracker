package com.bardab.budgettracker.dao;
import com.bardab.budgettracker.model.Transaction;
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

    public  Transaction findByID(long id){
    Transaction transaction=null;
    Session session = null;
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


}
