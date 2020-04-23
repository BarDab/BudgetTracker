package com.bardab.budgettracker.model.additional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MonthCode {

    public MonthCode() {
    }

    public static List<String> yearList () {
       return  List.of("2020","2021","2022","2023","2024"
            ,"2025","2026","2027","2028","2029","2030");
    }

    public static List<String> monthNames(){
        return List.of("January", "February","March","April","May","June",
                "July","August","September","October","November","December");
    }


    public static String createMonthCode(String fullMonthName, String year){
        String monthCode = year +"_"+ getNumberOfMonth(fullMonthName);
        if(validateMonthCode(monthCode)){
        return monthCode;
        }
        else return null;
    }
    public static String createMonthCode(Integer numberOfMonth, String year){
        String monthCode = year+"_"+ numberOfMonth;
        if(validateMonthCode(monthCode)){
            return monthCode;
        }
        else return null;
    }

    public static String createMonthCodeFromLocalDate(LocalDate localDate){
        return createMonthCode(localDate.getMonth().getValue(),String.valueOf(localDate.getYear()));
    }

    public static int getNumberOfDaysInMonth(String monthCode){
        YearMonth yearMonth = YearMonth.of(Integer.parseInt(monthCode.substring(0,3)),Integer.parseInt(monthCode.substring(5,6)));
        return yearMonth.lengthOfMonth();
    }

    public static Integer getNumberOfMonth(String fullMonthName) {
        List<String> monthNames = List.of("January", "February","March","April","May","June",
                "July","August","September","October","November","December");
        Map<String,Integer> monthList = new HashMap<>();

        for(Integer i =1;i<=12;i++){
            monthList.put(monthNames.get(i-1),i);
        }
        return monthList.get(fullMonthName);
    }



    public static boolean validateMonthCode(String yearMonth){
        try{
            Pattern pattern = Pattern.compile("(\\d{4})((_\\d{1,2})|)");
            Matcher matcher = pattern.matcher(yearMonth);

            if(Integer.parseInt(yearMonth.substring(0,4)) <2020){
                return false;
            }

            if (yearMonth.length() == 6 && Integer.parseInt(yearMonth.substring(5)) < 1) {
                return false;
            }
            else if (yearMonth.length() == 6 && Integer.parseInt(yearMonth.substring(5)) >= 1) {
                if (matcher.matches()) {
                    return true; }
            }
            if (Integer.parseInt(yearMonth.substring(5, 7)) <= 12 && matcher.matches()) {
                return true;
            }}
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        System.out.println("Wrong monthCode");
        return false;
    }



}
