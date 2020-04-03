package com.bardab.budgettracker.model;

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
    void getAndSetId() {
        transaction.setId(0000);
        assertEquals(00000,transaction.getId());
    }

    @Test
    void getAndSetType() {
        transaction.setType("Food");
        assertEquals("Food",transaction.getType());
    }

    @Test
    void getAndSetTransactionDate() {
        transaction.setTransactionDate(LocalDate.now());
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
        transaction.setTransactionDate(LocalDate.now());
        transaction.setType("");
        transaction.setId(0);

        assertEquals(String.format("Transaction{id=%s, type='%s', transactionDate=%s, value=%s, description='%s'}",transaction.getId(),transaction.getType(),
                transaction.getTransactionDate(),transaction.getValue(),transaction.getDescription()), transaction.toString());
    }
}