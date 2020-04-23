package com.bardab.budgettracker.model.additional;

import com.bardab.budgettracker.dao.MonthlyBalanceDao;
import com.bardab.budgettracker.dao.TransactionDao;
import com.bardab.budgettracker.dao.categories.FixedCostsRealDao;
import com.bardab.budgettracker.dao.categories.VariableCostsRealDao;
import com.bardab.budgettracker.model.MonthlyBalance;
import com.bardab.budgettracker.model.Transaction;
import com.bardab.budgettracker.model.categories.FixedCostsReal;
import com.bardab.budgettracker.model.categories.VariableCostsReal;
import com.bardab.budgettracker.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

public class TransactionInsertionManager {

    private static SessionFactory sessionFactory;
    private MonthlyBalanceDao monthlyBalanceDao;
    private MonthlyBalance monthlyBalance ;
    private VariableCostsReal variableCostsReal;
    private FixedCostsReal fixedCostsReal;
    public TransactionInsertionManager() {
        sessionFactory= HibernateUtil.getInstance().getSessionFactory();
        fixedCostsReal = new FixedCostsReal();
        variableCostsReal = new VariableCostsReal();
        monthlyBalance=new MonthlyBalance();
        monthlyBalanceDao = new MonthlyBalanceDao(this.sessionFactory);
    }

    public void insertTransactionAndUpdateActiveCategories(Transaction transaction){
        monthlyBalance.addTransaction(transaction);
        monthlyBalance.setVariableCostsReal(variableCostsReal);
        monthlyBalance.setFixedCostsReal(fixedCostsReal);
        monthlyBalanceDao.addTransaction(monthlyBalance);
    }


}
class MainInitializer{

    public static void main(String[] args) {
        Transaction transaction = new Transaction();
        transaction.setType("Food");
        transaction.setDescription("");
        transaction.setValue(10.0);
        transaction.setTransactionDateAndMonthCode(LocalDate.now());




    }

}











