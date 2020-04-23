package com.bardab.budgettracker.model.transactionTypes;

import java.util.List;

public final class TransactionTypesFixed {

    public TransactionTypesFixed() {
    }

    public static List<String> getTypes() {
        List<String> transactionTypes = List.of("Rent","Electricity","Gas","HealthCare","Entertainment","Transport",
                "Hobby","MobileNetwork","Internet","Other");
        return transactionTypes;
    }


}
