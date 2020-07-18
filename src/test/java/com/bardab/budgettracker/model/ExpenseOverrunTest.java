package com.bardab.budgettracker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExpenseOverrunTest {

    private ExpenseOverrun expenseOverrun;
    private Actual actual;
    private Budget budget;

    @BeforeEach
    void init(){
        expenseOverrun = new ExpenseOverrun();
        actual = new Actual();

        budget = new Budget();

    }


    @Test
    void setOverrunValues() {
    }

    @Test
    void setActualAndBudgetAndYearMonth() {
    }

    @Test
    void setYearMonth() {
    }
}