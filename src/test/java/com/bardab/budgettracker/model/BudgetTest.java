package com.bardab.budgettracker.model;

import com.bardab.budgettracker.model.categories.PlannedExpenses;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BudgetTest {

    Budget budget;
    int numberOfDaysInMonth ;
    LocalDate localDate;
    Integer monthCode;

    @BeforeEach
    public void setUp(){
        budget = new Budget();

        localDate = LocalDate.of(2020,6,10);
        numberOfDaysInMonth = localDate.lengthOfMonth();
        monthCode = 20206;
    }

    @Test
    void plainSettersAndGettersForNegativeValues(){
        // if value less than 0 method should return 0.0
        budget.setIncome(-10.0);
        budget.setInvestments(-10.0);
        budget.setTotalVariableExpenses(-10.0);
        assertEquals(0.0, budget.getIncome());
        assertEquals(0.0, budget.getInvestments());
        assertEquals(0.0, budget.getTotalVariableExpenses());
    }

    @Test
    void plainSettersAndGettersForPositiveValues(){
        // if value less than 0 method should return 0.0
        budget.setIncome(10.0);
        budget.setInvestments(10.0);
        budget.setTotalVariableExpenses(10.0);
        assertEquals(10.0, budget.getIncome());
        assertEquals(10.0, budget.getInvestments());
        assertEquals(10.0, budget.getTotalVariableExpenses());
    }


    @Test
    void checkIfNegativeValuesCannotBeAdded(){
        budget.setIncome(-33.0);
        budget.setTotalVariableExpenses(-33.0);
        budget.setInvestments(-33.0);

        assertNotEquals(-33, budget.getIncome());
        assertNotEquals(-33, budget.getTotalVariableExpenses());
        assertNotEquals(-33, budget.getInvestments());
    }

    @Test
    void setDailyAverageIncome() {
        int income = 900;
        budget.setIncome(900.0);
        budget.setMonthCode(monthCode);
        budget.getDailyAverageIncome();
        assertEquals(income/numberOfDaysInMonth, budget.getDailyAverageIncome());
    }

    @Test
    void setIncomeLeftForDailySpending() {
        // equation: this.incomeLeftForDailySpending = (income - totalFixedCosts - additionalSpending - savingGoal)/getNumberOfDaysInMonth();
        PlannedExpenses plannedExpenses = new PlannedExpenses();
        plannedExpenses.setMonthCode(monthCode);
//        fixedCostsForecast.setDefaultValues(10.0);
        Double totalFixedCosts = plannedExpenses.getTotalValue();


        budget.setIncome(900.0);
        budget.setInvestments(10.0);
        budget.setTotalVariableExpenses(10.0);
        budget.setPlannedExpenses(plannedExpenses);
        budget.setTotalSpending();
        budget.setMonthCode(monthCode);
        budget.getDailyAverageExpenses();

        Double expectedValue = (900 - totalFixedCosts - 10 -10)/numberOfDaysInMonth;
//        assertEquals(expectedValue, budget.setDailyAverageExpenses());

    }



    @Test
    void setTotalFixedCosts() {
        PlannedExpenses plannedExpenses = new PlannedExpenses();
//        fixedCostsForecast.setDefaultValues(10.0);
        Double totalFixedCosts = plannedExpenses.getTotalValue();

        budget.setPlannedExpenses(plannedExpenses);
        budget.setTotalSpending();

        assertEquals(totalFixedCosts, budget.getTotalSpending());
    }





}