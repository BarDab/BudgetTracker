package com.bardab.budgettracker.dao;
import com.bardab.budgettracker.model.Actual;
import com.bardab.budgettracker.model.SpendingManager;
import com.bardab.budgettracker.model.Transaction;
import com.bardab.budgettracker.model.additional.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.time.YearMonth;
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
    public Transaction findByYearMonth(YearMonth yearMonth) {
        Transaction transaction=null;
        Session session = null;
        try{
            session=sessionFactory.openSession();
            session.beginTransaction();
            transaction = (Transaction) session.bySimpleNaturalId(Transaction.class).load(yearMonth);

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
        Actual actual;
        SpendingManager spendingManager;

        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            actual = session.bySimpleNaturalId(Actual.class).load(transaction.getYearMonth());
            if(actual ==null){
                actual = TransactionInsertionManager.initializeMonthlyBalance(transaction);
            }
            spendingManager = session.bySimpleNaturalId(SpendingManager.class).load(transaction.getYearMonth());
            if(spendingManager ==null){
                spendingManager = new SpendingManager();
            }

            TransactionInsertionManager.manageTransactionInsertion(transaction, actual,spendingManager);

            session.save(actual);
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


    public List<Transaction> getAllTransactions(Integer monthCode) {
        List<Transaction> instancesOfAnnotatedClass = new ArrayList<>();
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            instancesOfAnnotatedClass.addAll(session.createQuery("FROM Transaction where monthCode = " + monthCode).list());

        } catch (Exception e) {
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
        return instancesOfAnnotatedClass;
    }



    public String queryCreator(LocalDate dateFrom,LocalDate dateTo,List<Category> listOfCategories){
        String firstDate ="'"+dateFrom.toString()+"'";
        String secondDate ="'"+ dateTo.toString()+"'";
        String categories="( category=";
        for(Category category:listOfCategories){
            if(listOfCategories.indexOf(category)==0){
                categories+="'"+category+"'";
            }
            else categories+=" or category="+"'"+category+"'";
            if(listOfCategories.indexOf(category)==listOfCategories.size()-1){
                categories+=")";
            }
        }
        return  "FROM Transaction where transactionDate between "+firstDate+" and "+secondDate+" and "+categories;
    }

    public List<Transaction> getAllTransactions(LocalDate dateFrom,LocalDate dateTo,List<Category> listOfCategories) {
        List<Transaction> instancesOfAnnotatedClass = new ArrayList<>();
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            instancesOfAnnotatedClass.addAll(session.createQuery(queryCreator(dateFrom,dateTo,listOfCategories)  ).list());

        } catch (Exception e) {
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
        return instancesOfAnnotatedClass;
    }





}
