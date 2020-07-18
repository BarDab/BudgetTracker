package com.bardab.budgettracker.model;

import com.bardab.budgettracker.model.additional.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

class SpendingManagerTest {

    SpendingManager spendingManager;

    @BeforeEach
    void init() {
        spendingManager = new SpendingManager();
        spendingManager.initializeFields();
    }

    @Test
    void getMapOfCategoriesWithValues() {
    }

    @Test
    void initializeFields() {
        spendingManager.initializeFields();
        assertNotNull(spendingManager.getSavings());
        assertNotNull(spendingManager.getSpent());
        assertNotNull(spendingManager.getCashAtHand());
        assertNotNull(spendingManager.getDaysToNextPayment());
        assertNotNull(spendingManager.getYearMonth());
        assertNotNull(spendingManager.getMapOfCategoriesWithValues());


    }

    @Test
    void updateWithTransactions() {
        Transaction transaction = new Transaction();
        transaction.setCategory(Category.ENTERTAINMENT);
        transaction.setValue(10.0);
        spendingManager.updateWithTransactions(transaction);
        assertEquals(10.0, spendingManager.getSpent());
        init();

        transaction = new Transaction();
        transaction.setCategory(Category.INCOME);
        transaction.setValue(200.0);
        spendingManager.updateWithTransactions(transaction);
        assertEquals(200, spendingManager.getCashAtHand());

        init();
        transaction = new Transaction();
        transaction.setCategory(Category.SAVINGS);
        transaction.setValue(200.0);
        spendingManager.updateWithTransactions(transaction);
        assertEquals(200.0, spendingManager.getSavings());


    }

    @Test
    void setYearMonth() {
    }

//    @Test
//    @DisplayName("Cash at hand - only positive double values")
//    void setCashAtHand() {
//        spendingManager.setCashAtHand(10.0);
//        assertEquals(10.0, spendingManager.getCashAtHand());
//        spendingManager.setCashAtHand(-10.0);
//        assertNotEquals(-10.0, spendingManager.getCashAtHand());
//    }
//
//    @Test
//    @DisplayName("Savings - only positive double values")
//    void setSavings() {
//        spendingManager.setSavings(10.0);
//        assertEquals(10.0, spendingManager.getSavings());
//        spendingManager.setSavings(-10.0);
//        assertNotEquals(-10.0, spendingManager.getSavings());
//    }


    @Test
    void getDaysToNextPayment() {
        LocalDate date1 = LocalDate.now();
        int dayOfPayment = 5;
        LocalDate date2 = LocalDate.of(2020, date1.getMonthValue(), dayOfPayment);
        int days;

        for (int i = 0; i <= 1; i++) {
            if (dayOfPayment < date1.getDayOfMonth()) {
                days = Period.between(date1, date2.plusMonths(1)).getDays();
            } else {
                date2 = LocalDate.of(2020, date1.getMonthValue(), dayOfPayment);
                days = Period.between(date1, date2).getDays();
            }

            spendingManager.setDaysToNextPayment(dayOfPayment);
            assertEquals(days, spendingManager.getDaysToNextPayment());
            dayOfPayment = 28;
        }
        spendingManager.setDaysToNextPayment(40);
        assertNotEquals(40, spendingManager.getDaysToNextPayment());
        spendingManager.setDaysToNextPayment(0);
        assertNotEquals(0, spendingManager.getDaysToNextPayment());
        spendingManager.setDaysToNextPayment(-10);
        assertNotEquals(-10, spendingManager.getDaysToNextPayment());
    }
}