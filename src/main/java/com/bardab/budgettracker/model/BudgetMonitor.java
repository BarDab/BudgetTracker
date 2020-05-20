package com.bardab.budgettracker.model;

import com.bardab.budgettracker.gui.DoubleFormatter;
import com.bardab.budgettracker.model.categories.Categories;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashMap;

public class BudgetMonitor {







    public static HashMap<String, Double> comparedValues(MonthlyBalance monthlyBalance,Budget budget){
        HashMap<String,Double> comparedValuesMap = new HashMap<>();

        for(String keyBalance: Categories.getMapOfCategories(monthlyBalance).keySet()){
            for(String keyBudget: Categories.getMapOfCategories(budget).keySet()){
                if(keyBalance.equals(keyBudget)){
                    Double comparison = 0.0;
                    try {
                        comparison = Categories.getMapOfCategories(monthlyBalance).get(keyBalance) / Categories.getMapOfCategories(budget).get(keyBudget);


                    }
                    catch (Exception e){
                       e.printStackTrace();
                   }

                   comparedValuesMap.put(keyBalance,comparison);
                }
            }
        }
        return comparedValuesMap;


    }




}
