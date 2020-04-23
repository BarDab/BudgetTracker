package com.bardab.budgettracker.model;

import com.bardab.budgettracker.model.additional.MonthCode;
import com.bardab.budgettracker.model.categories.FixedCostsForecast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class FixedCostsForecastTest {

    FixedCostsForecast fixedCostsForecast;

    int numberOfDaysInMonth ;
    LocalDate localDate;
    String monthCode;

    @BeforeEach
    void beforeEach(){
        this.fixedCostsForecast = new FixedCostsForecast();

        localDate = LocalDate.of(2020,6,10);
        numberOfDaysInMonth = localDate.lengthOfMonth();
        monthCode = MonthCode.createMonthCodeFromLocalDate(localDate);

    }



    @Test
    void plainSettersAndGettersForNegativeValues() {
        // if value less than 0 method should return 0.0
        fixedCostsForecast.setOther(-10.0);
        fixedCostsForecast.setRent(-10.0);
        fixedCostsForecast.setTransport(-10.0);
        fixedCostsForecast.setHobby(-10.0);
        fixedCostsForecast.setHealthCare(-10.0);
        fixedCostsForecast.setGas(-10.0);
        fixedCostsForecast.setEntertainment(-10.0);
        fixedCostsForecast.setElectricity(-10.0);

        assertEquals(0.0, fixedCostsForecast.getOther());
        assertEquals(0.0, fixedCostsForecast.getRent());
        assertEquals(0.0, fixedCostsForecast.getTransport());
        assertEquals(0.0, fixedCostsForecast.getHobby());
        assertEquals(0.0, fixedCostsForecast.getHealthCare());
        assertEquals(0.0, fixedCostsForecast.getGas());
        assertEquals(0.0, fixedCostsForecast.getElectricity());
        assertEquals(0.0, fixedCostsForecast.getEntertainment());
    }

    @Test
    void plainSettersAndGettersForPositiveValues(){
        // if value less than 0 method should return 0.0
        fixedCostsForecast.setOther(10.0);
        fixedCostsForecast.setRent(10.0);
        fixedCostsForecast.setTransport(10.0);
        fixedCostsForecast.setHobby(10.0);
        fixedCostsForecast.setHealthCare(10.0);
        fixedCostsForecast.setGas(10.0);
        fixedCostsForecast.setEntertainment(10.0);
        fixedCostsForecast.setElectricity(10.0);


        assertEquals(10.0, fixedCostsForecast.getOther());
        assertEquals(10.0, fixedCostsForecast.getRent());
        assertEquals(10.0, fixedCostsForecast.getTransport());
        assertEquals(10.0, fixedCostsForecast.getHobby());
        assertEquals(10.0, fixedCostsForecast.getHealthCare());
        assertEquals(10.0, fixedCostsForecast.getGas());
        assertEquals(10.0, fixedCostsForecast.getElectricity());
        assertEquals(10.0, fixedCostsForecast.getEntertainment());

    }

    @Test
    void getFixedCostsTypesWithValuesFromToStringMethod() {
        fixedCostsForecast.setDefaultValues(10.0);

        LinkedHashMap<String,Double> testMap = new LinkedHashMap<>();
        testMap.put("electricity",10.0);
        testMap.put("gas",10.0);
        testMap.put("rent",10.0);
        testMap.put("otherUtilities",10.0);
        testMap.put("healthCare",10.0);
        testMap.put("entertainment",10.0);
        testMap.put("hobby",10.0);
        testMap.put("transport",10.0);

        assertEquals(testMap, fixedCostsForecast.getFixedCostsTypesWithValuesFromToStringMethod());
    }

}