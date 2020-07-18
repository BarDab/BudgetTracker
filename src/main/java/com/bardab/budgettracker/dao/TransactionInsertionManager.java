package com.bardab.budgettracker.dao;

import com.bardab.budgettracker.model.*;
import com.bardab.budgettracker.model.additional.Category;

import java.time.YearMonth;

public class TransactionInsertionManager {

    public static void manageTransactionInsertion(Transaction transaction, Actual actual,SpendingManager spendingManager){

        spendingManager.initializeFields();
        spendingManager.updateWithTransactions(transaction);

            if(transaction.getCategory().equals(Category.INCOME)) {
                actual.getActualIncome().updateCategoryValue(transaction.getCategory(),transaction.getValue());
            }
            else if (transaction.getCategory().equals(Category.SAVINGS)){
                actual.getActualSavings().updateCategoryValue(transaction.getCategory(),transaction.getValue());
            }
            else actual.getActualExpenses().updateCategoryValue(transaction.getCategory(),transaction.getValue());


    }

    public static Actual initializeMonthlyBalance(Transaction transaction){
        Actual actual = new Actual();
        actual.setYearMonth(transaction.getYearMonth());
        ActualExpenses actualExpenses = new ActualExpenses();
        actualExpenses.initializeCategoryValues();
        ActualIncome actualIncome = new ActualIncome();
        actualIncome.initializeCategoryValues();
        ActualSavings actualSavings = new ActualSavings();
        actualSavings.initializeCategoryValues();


        actual.setActualExpenses(actualExpenses);
        actual.setActualIncome(actualIncome);
        actual.setActualSavings(actualSavings);
        return actual;
    }

    public static Budget initializeBudget(YearMonth yearMonth){
        Budget budget = new Budget();
        budget.setYearMonth(yearMonth);
        BudgetExpenses BudgetExpenses = new BudgetExpenses();
        BudgetExpenses.initializeCategoryValues();
        BudgetIncome BudgetIncome = new BudgetIncome();
        BudgetIncome.initializeCategoryValues();
        BudgetSavings BudgetSavings = new BudgetSavings();
        BudgetSavings.initializeCategoryValues();


        budget.setBudgetExpenses(BudgetExpenses);
        budget.setBudgetIncome(BudgetIncome);
        budget.setBudgetSavings(BudgetSavings);
        System.out.println(budget.getBudgetIncome());
        return budget;
    }

    public static ExpenseOverrun setOverrun(Actual actual, Budget budget){
        ExpenseOverrun expenseOverrun = new ExpenseOverrun();
        if(actual!=null&budget!=null) {
            expenseOverrun.setActualAndBudgetAndYearMonth(actual, budget,actual.getYearMonth());
            expenseOverrun.initializeCategoryValues();
            expenseOverrun.setOverrunValues();
        }
        return expenseOverrun;
    }




}












