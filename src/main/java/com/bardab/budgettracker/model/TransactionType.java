package com.bardab.budgettracker.model;
import java.util.List;

public class TransactionType {

    public TransactionType() {
    }

    public static List<String> getTransactionTypes() {
        List<String> transactionTypes = List.of("Food", "Household","Shopping","Health","Bills","Transport",
                "Hobby","Dining","Events","Gifts","Investments","Travel","Fees","Other");

        return transactionTypes;
    }
}
