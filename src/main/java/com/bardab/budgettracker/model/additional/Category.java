package com.bardab.budgettracker.model.additional;


import java.util.List;

public enum Category {

    TOTALEXPENSES,
    BILLS,
    CLOTHING,
    DEBT,
    DINING,
    ENTERTAINMENT,
    FOOD,
    HEALTHCARE,
    HOBBY,
    INCOME,
    OTHER,
    SAVINGS,
    TRANSPORT;

    public static List<Category> expenses(){
        return List.of(
                BILLS,
                CLOTHING,
                DEBT,
                DINING,
                ENTERTAINMENT,
                FOOD,
                HEALTHCARE,
                HOBBY,
                OTHER,
                TRANSPORT

        );
    }

}


