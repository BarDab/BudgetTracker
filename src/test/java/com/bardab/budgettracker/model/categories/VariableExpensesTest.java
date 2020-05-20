package com.bardab.budgettracker.model.categories;

import com.bardab.budgettracker.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VariableExpensesTest {

    VariableExpenses variableExpenses;

    @BeforeEach
    void init(){
    variableExpenses = new VariableExpenses();
    }

    @Test
    void getFieldsWithValues(){
        variableExpenses.initializeFields(variableExpenses);
        variableExpenses.getMapOfCategories(variableExpenses).forEach((e,f)->
                System.out.println(e+" "+f)  );
    }

    @Test
    void updateVariable(){
        variableExpenses.initializeFields(variableExpenses);
        variableExpenses.updateValue(variableExpenses,"food",10.0);
        assertEquals(10.0,variableExpenses.getFood());

    }

    @Test
    void getTotalValue(){
        Transaction transaction = new Transaction();
        transaction.setCategoryAndTransformToCamelCase("food");
        transaction.setValue(10.0);
        variableExpenses.addTransactionValue(transaction);
        assertEquals(10.0,variableExpenses.getTotalValue());

    }



}