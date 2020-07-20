package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.dao.TransactionInsertionManager;
import com.bardab.budgettracker.model.Budget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;

class NewBudgetFXControllerTest {

    NewBudgetFXController newBudgetFXController;
    Budget budget;

    @BeforeEach
    public void init(){
        newBudgetFXController = new NewBudgetFXController();

        this.budget = TransactionInsertionManager.initializeBudget(YearMonth.of(2020,10));
        this.budget.getBudgetExpenses().initializeCategoryValues();
        this.budget.getBudgetIncome().initializeCategoryValues();
        this.budget.getBudgetSavings().initializeCategoryValues();
    }


    @Test
    void populateMapOfCategoryLabelsWithValuesTextFields() {
        newBudgetFXController.populateMapOfCategoryLabelsWithValuesTextFields(budget).keySet().forEach(
                e-> System.out.println(e.getText())
        );
    }
}