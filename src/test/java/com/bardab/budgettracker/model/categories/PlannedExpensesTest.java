package com.bardab.budgettracker.model.categories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlannedExpensesTest {


    PlannedExpenses plannedExpenses;

    @BeforeEach
    void init(){
        plannedExpenses = new PlannedExpenses();
    }

    @Test
    void getFieldsWithValues(){
        plannedExpenses.initializeFields(plannedExpenses);
        plannedExpenses.getMapOfCategories(plannedExpenses).forEach((e, f)->
                System.out.println(e+" "+f)  );
    }

}