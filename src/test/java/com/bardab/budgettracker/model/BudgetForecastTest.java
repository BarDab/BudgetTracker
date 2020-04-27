package com.bardab.budgettracker.model;

import com.bardab.budgettracker.model.additional.MonthCode;
import com.bardab.budgettracker.model.categories.FixedCostsForecast;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BudgetForecastTest {

    BudgetForecast budgetForecast;
    int numberOfDaysInMonth ;
    LocalDate localDate;
    String monthCode;

    @BeforeEach
    public void setUp(){
        budgetForecast = new BudgetForecast();

        localDate = LocalDate.of(2020,6,10);
        numberOfDaysInMonth = localDate.lengthOfMonth();
        monthCode = MonthCode.createMonthCodeFromLocalDate(localDate);
    }

    @Test
    void plainSettersAndGettersForNegativeValues(){
        // if value less than 0 method should return 0.0
        budgetForecast.setIncome(-10.0);
        budgetForecast.setSavingGoal(-10.0);
        budgetForecast.setAdditionalSpending(-10.0);
        assertEquals(0.0,budgetForecast.getIncome());
        assertEquals(0.0,budgetForecast.getSavingGoal());
        assertEquals(0.0,budgetForecast.getAdditionalSpending());
    }

    @Test
    void plainSettersAndGettersForPositiveValues(){
        // if value less than 0 method should return 0.0
        budgetForecast.setIncome(10.0);
        budgetForecast.setSavingGoal(10.0);
        budgetForecast.setAdditionalSpending(10.0);
        assertEquals(10.0,budgetForecast.getIncome());
        assertEquals(10.0,budgetForecast.getSavingGoal());
        assertEquals(10.0,budgetForecast.getAdditionalSpending());
    }


    @Test
    void checkIfNegativeValuesCannotBeAdded(){
        budgetForecast.setIncome(-33.0);
        budgetForecast.setAdditionalSpending(-33.0);
        budgetForecast.setSavingGoal(-33.0);

        assertNotEquals(-33,budgetForecast.getIncome());
        assertNotEquals(-33,budgetForecast.getAdditionalSpending());
        assertNotEquals(-33,budgetForecast.getSavingGoal());
    }

    @Test
    void setDailyAverageIncome() {
        int income = 900;
        budgetForecast.setIncome(900.0);
        budgetForecast.setMonthCode(monthCode);
        budgetForecast.setDailyAverageIncome();
        assertEquals(income/numberOfDaysInMonth,budgetForecast.getDailyAverageIncome());
    }

    @Test
    void setIncomeLeftForDailySpending() {
        // equation: this.incomeLeftForDailySpending = (income - totalFixedCosts - additionalSpending - savingGoal)/getNumberOfDaysInMonth();
        FixedCostsForecast fixedCostsForecast = new FixedCostsForecast();
        fixedCostsForecast.setMonthCode(monthCode);
        fixedCostsForecast.setDefaultValues(10.0);
        Double totalFixedCosts = fixedCostsForecast.getSumOfFixedCosts();


        budgetForecast.setIncome(900.0);
        budgetForecast.setSavingGoal(10.0);
        budgetForecast.setAdditionalSpending(10.0);
        budgetForecast.setFixedCostsForecast(fixedCostsForecast);
        budgetForecast.setTotalFixedCosts();
        budgetForecast.setMonthCode(monthCode);
        budgetForecast.setAverageDailyFundsAfterDeductionOfAllCosts();

        Double expectedValue = (900 - totalFixedCosts - 10 -10)/numberOfDaysInMonth;
        assertEquals(expectedValue,budgetForecast.getAverageDailyFundsAfterDeductionOfAllCosts());

    }



    @Test
    void setTotalFixedCosts() {
        FixedCostsForecast fixedCostsForecast = new FixedCostsForecast();
        fixedCostsForecast.setDefaultValues(10.0);
        Double totalFixedCosts = fixedCostsForecast.getSumOfFixedCosts();

        budgetForecast.setFixedCostsForecast(fixedCostsForecast);
        budgetForecast.setTotalFixedCosts();

        assertEquals(totalFixedCosts,budgetForecast.getTotalFixedCosts());
    }



    @Test
    void setMonthCode() {
        budgetForecast.setMonthCode("2024_2");
        assertEquals("2024_2",budgetForecast.getMonthCode());
    }
    @Test
    void validateMonthCode(){
    assertFalse(budgetForecast.validateMonthCode("January2020"));
    assertFalse(budgetForecast.validateMonthCode(""));
    assertFalse(budgetForecast.validateMonthCode("1900_10"));
    assertFalse(budgetForecast.validateMonthCode("2021_31"));
    assertFalse(budgetForecast.validateMonthCode("2021_0"));
    assertFalse(budgetForecast.validateMonthCode("202_11"));
    assertFalse(budgetForecast.validateMonthCode("20223_1"));

    assertTrue(budgetForecast.validateMonthCode("2021_1"));
    assertTrue(budgetForecast.validateMonthCode("2020_11"));
    }


}