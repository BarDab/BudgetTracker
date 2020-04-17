package com.bardab.budgettracker.model;

import org.junit.jupiter.api.*;

import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.*;

class BudgetForecastTest {

    BudgetForecast budgetForecast;

    @BeforeEach
    public void setUp(){
        budgetForecast = new BudgetForecast();
    }
    @Test
    public void checkIfNegativeValuesCannotBeAdded(){
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
        int numberOfDaysInMonth = YearMonth.of(2020,6).lengthOfMonth();
        budgetForecast.setIncome(900.0);
        budgetForecast.setMonthCode("2020_6");
        budgetForecast.setDailyAverageIncome();
        assertEquals(income/numberOfDaysInMonth,budgetForecast.getDailyAverageIncome());
    }


    @Test
    @DisplayName("Check if negative values cannot be added")
    void setFixedSpending() {
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