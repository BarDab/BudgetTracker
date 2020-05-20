package com.bardab.budgettracker.model.categories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FixedExpensesTest {


    FixedExpenses fixedExpenses;

    @BeforeEach
    void init(){
        fixedExpenses = new FixedExpenses();
    }

    @Test
    void getFieldsWithValues(){
        fixedExpenses.initializeFields(fixedExpenses);
        fixedExpenses.getMapOfCategories(fixedExpenses).forEach((e, f)->
                System.out.println(e+" "+f)  );
    }

    @Test
    void getNames(){
        fixedExpenses.getCategoriesNames(fixedExpenses).forEach(e-> System.out.println(e));
    }

}