package com.bardab.budgettracker.gui.additional;

import java.time.Month;
import java.util.List;


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

        public static String getMonthInPresentable(Month month){
            return month.toString().toLowerCase().substring(0,1).toUpperCase()+month.toString().toLowerCase().substring(1);
        }



    }
