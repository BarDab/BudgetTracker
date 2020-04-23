package com.bardab.budgettracker.model.transactionTypes;
import java.util.List;

public final class TransactionTypesVariable {

    public TransactionTypesVariable() {
    }

    public static List<String> getTypes() {
        List<String> transactionTypes = List.of("Food", "Household","Clothing","Health","Bills","Transport",
                "Hobby","Dining","Events","Gifts","Investments","Travel","Fees","Development","Books", "Other");
        return transactionTypes;
    }
}
