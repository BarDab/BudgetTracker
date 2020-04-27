package com.bardab.budgettracker.dao;

import com.bardab.budgettracker.dao.BudgetForecastDao;
import com.bardab.budgettracker.dao.MonthlyBalanceDao;
import com.bardab.budgettracker.dao.TransactionDao;
import com.bardab.budgettracker.model.BudgetForecast;
import com.bardab.budgettracker.model.MonthlyBalance;
import com.bardab.budgettracker.model.Transaction;
import com.bardab.budgettracker.model.categories.FixedCosts;
import com.bardab.budgettracker.model.categories.FixedCostsForecast;
import com.bardab.budgettracker.model.categories.VariableCosts;
import com.bardab.budgettracker.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

public class TransactionInsertionManager {

    private static SessionFactory sessionFactory;
    private MonthlyBalanceDao monthlyBalanceDao;
    private TransactionDao transactionDao;
    private MonthlyBalance monthlyBalance;
    private VariableCosts variableCosts;
    private FixedCosts fixedCosts;

    public  TransactionInsertionManager() {
        sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        fixedCosts = new FixedCosts();
        variableCosts = new VariableCosts();
        monthlyBalance = new MonthlyBalance();
//        monthlyBalanceDao = new MonthlyBalanceDao(this.sessionFactory);
        transactionDao = new TransactionDao(this.sessionFactory);
    }

    public void insertTransactionAndUpdateActiveCategories(Transaction transaction) {
            transactionDao.addTransactionAndUpdateAllFields(transaction);
    }
}












