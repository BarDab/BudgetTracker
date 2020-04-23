package com.bardab.budgettracker.model;

import com.bardab.budgettracker.model.additional.MonthCode;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MonthCodeTest {

    String correctMonthCode = "2022_10";
    String incorrectDateMonthCode = "1999_10";
    String monthName = "October";

    int correctNumberOfMonth = 10;
    int correctYear = 2022;


    LocalDate localDate = LocalDate.of(correctYear,correctNumberOfMonth,1);
    int numberOfDaysInMonth = localDate.lengthOfMonth();

    @Test
    void createMonthCode() {
     assertEquals(correctMonthCode, MonthCode.createMonthCode(monthName,String.valueOf(correctYear)));
     assertNull(null,MonthCode.createMonthCode(incorrectDateMonthCode,incorrectDateMonthCode));
    }

    @Test
    void CreateMonthCode() {
    assertEquals(correctMonthCode,MonthCode.createMonthCode(correctNumberOfMonth,String.valueOf(correctYear)));
        assertNull(null,MonthCode.createMonthCode(13,incorrectDateMonthCode));
    }

    @Test
    void createMonthCodeFromLocalDate() {
    assertEquals(correctMonthCode,MonthCode.createMonthCodeFromLocalDate(localDate));
    }

    @Test
    void getNumberOfDaysInMonth() {
        assertEquals(numberOfDaysInMonth,MonthCode.getNumberOfDaysInMonth(correctMonthCode));
    }

    @Test
    void getNumberOfMonth() {
        assertEquals(correctNumberOfMonth,MonthCode.getNumberOfMonth("October"));
    }

    @Test
    void validateMonthCode() {
        assertFalse(MonthCode.validateMonthCode("January2020"));
        assertFalse(MonthCode.validateMonthCode(""));
        assertFalse(MonthCode.validateMonthCode("1900_10"));
        assertFalse(MonthCode.validateMonthCode("2021_31"));
        assertFalse(MonthCode.validateMonthCode("2021_0"));
        assertFalse(MonthCode.validateMonthCode("202_11"));
        assertFalse(MonthCode.validateMonthCode("20223_1"));

        assertTrue(MonthCode.validateMonthCode("2021_1"));
        assertTrue(MonthCode.validateMonthCode("2020_11"));
    }
}