package com.bardab.budgettracker.model;

import com.bardab.budgettracker.model.additional.MonthCode;
import com.bardab.budgettracker.model.categories.PlannedExpenses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class CostsBudgetTest {

    PlannedExpenses plannedExpenses;

    int numberOfDaysInMonth ;
    LocalDate localDate;
    String monthCode;

    @BeforeEach
    void beforeEach(){
        this.plannedExpenses = new PlannedExpenses();

        localDate = LocalDate.of(2020,6,10);
        numberOfDaysInMonth = localDate.lengthOfMonth();
        monthCode = MonthCode.createMonthCodeFromLocalDate(localDate);

    }




    @Test
    void getFixedCostsTypesWithValuesFromToStringMethod() {
//        fixedCostsForecast.setDefaultValues(10.0);

        LinkedHashMap<String,Double> testMap = new LinkedHashMap<>();
        testMap.put("electricity",10.0);
        testMap.put("gas",10.0);
        testMap.put("rent",10.0);
        testMap.put("otherUtilities",10.0);
        testMap.put("healthCare",10.0);
        testMap.put("entertainment",10.0);
        testMap.put("hobby",10.0);
        testMap.put("transport",10.0);

//        assertEquals(testMap, plannedExpenses.getFixedCostsTypesWithValuesFromToStringMethod());
    }

}