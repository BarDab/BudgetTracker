package com.bardab.budgettracker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class FixedCostsTest {

    FixedCosts fixedCosts;

    int numberOfDaysInMonth ;
    LocalDate localDate;
    String monthCode;

    @BeforeEach
    void beforeEach(){
        this.fixedCosts = new FixedCosts();

        localDate = LocalDate.of(2020,6,10);
        numberOfDaysInMonth = localDate.lengthOfMonth();
        monthCode = MonthCode.createMonthCodeFromLocalDate(localDate);

    }



    @Test
    void plainSettersAndGettersForNegativeValues() {
        // if value less than 0 method should return 0.0
        fixedCosts.setOtherUtilities(-10.0);
        fixedCosts.setRent(-10.0);
        fixedCosts.setTransport(-10.0);
        fixedCosts.setHobby(-10.0);
        fixedCosts.setHealthCare(-10.0);
        fixedCosts.setGas(-10.0);
        fixedCosts.setEntertainment(-10.0);
        fixedCosts.setElectricity(-10.0);

        assertEquals(0.0,fixedCosts.getOtherUtilities());
        assertEquals(0.0,fixedCosts.getRent());
        assertEquals(0.0,fixedCosts.getTransport());
        assertEquals(0.0,fixedCosts.getHobby());
        assertEquals(0.0,fixedCosts.getHealthCare());
        assertEquals(0.0,fixedCosts.getGas());
        assertEquals(0.0,fixedCosts.getElectricity());
        assertEquals(0.0,fixedCosts.getEntertainment());
    }

    @Test
    void plainSettersAndGettersForPositiveValues(){
        // if value less than 0 method should return 0.0
        fixedCosts.setOtherUtilities(10.0);
        fixedCosts.setRent(10.0);
        fixedCosts.setTransport(10.0);
        fixedCosts.setHobby(10.0);
        fixedCosts.setHealthCare(10.0);
        fixedCosts.setGas(10.0);
        fixedCosts.setEntertainment(10.0);
        fixedCosts.setElectricity(10.0);


        assertEquals(10.0,fixedCosts.getOtherUtilities());
        assertEquals(10.0,fixedCosts.getRent());
        assertEquals(10.0,fixedCosts.getTransport());
        assertEquals(10.0,fixedCosts.getHobby());
        assertEquals(10.0,fixedCosts.getHealthCare());
        assertEquals(10.0,fixedCosts.getGas());
        assertEquals(10.0,fixedCosts.getElectricity());
        assertEquals(10.0,fixedCosts.getEntertainment());

    }

    @Test
    void getFixedCostsTypesWithValuesFromToStringMethod() {
        fixedCosts.setDefaultValues(10.0);

        LinkedHashMap<String,Double> testMap = new LinkedHashMap<>();
        testMap.put("electricity",10.0);
        testMap.put("gas",10.0);
        testMap.put("rent",10.0);
        testMap.put("otherUtilities",10.0);
        testMap.put("healthCare",10.0);
        testMap.put("entertainment",10.0);
        testMap.put("hobby",10.0);
        testMap.put("transport",10.0);

        assertEquals(testMap,fixedCosts.getFixedCostsTypesWithValuesFromToStringMethod());
    }

}