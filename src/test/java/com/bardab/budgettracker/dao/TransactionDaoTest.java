package com.bardab.budgettracker.dao;

import com.bardab.budgettracker.model.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDaoTest {
    private SessionFactory sessionFactory;
    private TransactionDao transactionDao;
    private Transaction transaction;

    private final Double value = 1.33;
    private final String description = "sample";
    private final LocalDate localDate = LocalDate.now();
    private final String type = "test";
    private final long id = 1;


    @BeforeEach
    public void before() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Transaction.class);
        configuration.setProperty("hibernate.dialect",
                "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class",
                "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./mem");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        sessionFactory = configuration.buildSessionFactory();
        transactionDao = new TransactionDao(sessionFactory);
        transaction = new Transaction();
        transaction.setValue(value);
        transaction.setDescription(description);
        transaction.setTransactionDateAndMonthCode(localDate);
        transaction.setType(type);
    }
    @AfterEach
    public void AfterAll(){
        sessionFactory.close();
    }

    @Test
    void addTransaction(){
        transactionDao.addEntity(transaction);
        Transaction fromDB = transactionDao.findByID(transaction.getId());

        assertNotNull(fromDB);
        assertEquals(fromDB.getDescription(),transaction.getDescription());
        assertEquals(fromDB.getType(),transaction.getType());
        assertEquals(fromDB.getValue(),transaction.getValue());
        assertEquals(fromDB.getId(),transaction.getId());
    }
    @Test
    void deleteTransaction(){
        transactionDao.addEntity(transaction);
        Transaction fromDB = transactionDao.findByID(id);
        transactionDao.deleteTransaction(fromDB.getId());
        assertNull(transactionDao.findByID(fromDB.getId()));
    }
    @Test
    void updateTransaction(){
        Transaction updatedTransaction = new Transaction();
        updatedTransaction.setDescription("updated transaction's description");
        updatedTransaction.setType("updated transaction's type");
        updatedTransaction.setValue(9.99);
        updatedTransaction.setTransactionDateAndMonthCode(LocalDate.of(1999,12,31));

        transactionDao.addEntity(transaction);
        transactionDao.updateTransaction(id,updatedTransaction);
        Transaction fromDB = transactionDao.findByID(id);
        assertEquals("updated transaction's description",fromDB.getDescription());
        assertEquals("updated transaction's type",fromDB.getType());
        assertEquals(9.99,fromDB.getValue());
        assertEquals(LocalDate.of(1999,12,31),fromDB.getTransactionDate());
        assertEquals(id,fromDB.getId());
    }

    @Test
    void displayAllTest(){
        List<Transaction> transactions = new ArrayList<>();
        for(int i = 0;i<10;i++){
        Transaction transaction = new Transaction();
        transaction.setValue(i+0.1);
        transaction.setDescription("Description number "+i);
        transaction.setTransactionDateAndMonthCode(localDate);
        transaction.setType("Type number "+i);
        transactionDao.addEntity(transaction);
        transactions.add(transaction);
        }
        assertEquals(10,transactionDao.displayAllTransactions("Transaction").size());
        assertEquals(transactions,transactionDao.displayAllTransactions("Transaction"));
    }
    @Test
    void deleteAllTest(){
        for(int i = 0;i<10;i++){
            Transaction transaction = new Transaction();
            transaction.setValue(i+0.1);
            transaction.setDescription("Description number "+i);
            transaction.setTransactionDateAndMonthCode(localDate);
            transaction.setType("Type number "+i);
            transactionDao.addEntity(transaction);
        }
        transactionDao.deleteAllTransactions("Transaction");
        assertEquals(0,transactionDao.displayAllTransactions("Transaction").size());
    }
}