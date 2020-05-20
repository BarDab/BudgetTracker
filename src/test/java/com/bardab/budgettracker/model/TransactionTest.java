package com.bardab.budgettracker.model;

import com.bardab.budgettracker.model.additional.MonthCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    Transaction transaction;

    @BeforeEach
    void transactionInstance(){
        transaction = new Transaction();
    }

    @Test
    void setMonthCode(){
        LocalDate localDate = LocalDate.of(2030,6,10);
        String month = MonthCode.createMonthCodeFromLocalDate(localDate);
        transaction.setTransactionDateAndMonthCode(localDate);
        assertEquals(month,transaction.getMonthCode());
    }


    @Test
    void equals(){
        Transaction comparedTransaction = new Transaction();
        assertTrue(this.transaction.equals(transaction));
        assertTrue(comparedTransaction.equals(transaction));
        comparedTransaction.setValue(10.0);
        assertFalse(comparedTransaction.equals(transaction));
        assertFalse(transaction.equals(null));
    }

    @Test
    void getAndSetId() {
        transaction.setId(0000);
        assertEquals(00000,transaction.getId());
    }

    @Test
    void  setCategoryAndTransformToCamelCase() {
        transaction.setCategoryAndTransformToCamelCase("Food");
        assertEquals("food",transaction.getCategory());

        transaction.setCategoryAndTransformToCamelCase("Health Care");
        assertEquals("healthCare",transaction.getCategory());
    }

    @Test
    void getAndSetTransactionDate() {
        transaction.setTransactionDateAndMonthCode(LocalDate.now());
        assertEquals(LocalDate.now(),transaction.getTransactionDate());
    }

    @Test
    void getAndSetValue() {
        transaction.setValue(0.11);
        assertEquals(0.11,transaction.getValue());
    }

    @Test
    @DisplayName("Check if negative values cannot be added")
    void checkIfValueNotNegative(){
        transaction.setValue(-0.33);
        assertNotEquals(-0.33,transaction.getValue());
    }

    @Test
    void getAndSetDescription() {
        transaction.setDescription("description");
        assertEquals("description",transaction.getDescription());
    }

    @Test
    public void testToString() {
        transaction = new Transaction();
        transaction.setValue(0.00);
        transaction.setDescription("");
        transaction.setTransactionDateAndMonthCode(LocalDate.now());
        transaction.setCategoryAndTransformToCamelCase("");
        transaction.setId(0);

        assertEquals(String.format("Transaction{id=%s, type='%s', transactionDate=%s, value=%s, description='%s'}",transaction.getId(),transaction.getCategory(),
                transaction.getTransactionDate(),transaction.getValue(),transaction.getDescription()), transaction.toString());
    }
}