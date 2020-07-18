package com.bardab.budgettracker.model;

import com.bardab.budgettracker.model.additional.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TransactionTest {
    Transaction transaction;

    @BeforeEach
    void init(){
        transaction = new Transaction();
    }


    @Test
    void setCategory() {
        transaction.setCategory(Category.INCOME);
        assertEquals(Category.INCOME,transaction.getCategory());
    }

    @Test
    void setTransactionDate() {
        transaction.setTransactionDate(LocalDate.now());
        assertEquals(LocalDate.now(),transaction.getTransactionDate());
    }

    @Test
    @DisplayName("Only positive values")
    void setValue() {
        transaction.setValue(5.0);
        transaction.setValue(-10.0);
        assertNotEquals(-10.0,transaction.getValue());
        assertEquals(5.0,transaction.getValue());
    }

    @Test
    void getYearMonth(){
        transaction.setTransactionDate(LocalDate.now());
        assertEquals(YearMonth.now(),transaction.getYearMonth());
    }



    @Test
    void setDescription() {
        transaction.setDescription("TEST");
        assertEquals("TEST",transaction.getDescription());
        assertNotEquals("tEst",transaction.getDescription());
    }
}