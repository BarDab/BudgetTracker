package com.bardab.budgettracker.dao;

import com.bardab.budgettracker.model.MonthlyBalance;
import com.bardab.budgettracker.model.Transaction;
import com.bardab.budgettracker.model.categories.FixedExpenses;
import com.bardab.budgettracker.model.categories.VariableExpenses;
import com.bardab.budgettracker.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class TransactionInsertionManager {

    private static SessionFactory sessionFactory;
    private TransactionDao transactionDao;

    public TransactionInsertionManager() {
        sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        transactionDao = new TransactionDao(this.sessionFactory);
    }





    public void insertTransactionAndUpdateActiveCategories(Transaction transaction) {
        transactionDao.addTransactionAndUpdateAllFields(transaction);
    }
}












