package com.bardab.budgettracker.gui;

import com.bardab.budgettracker.model.categories.VariableExpenses;
import javafx.scene.chart.XYChart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

class ChartDataTest {

    private ChartData chartData;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private List<String> categories;
    private VariableExpenses variableExpenses;
    @BeforeEach
    void setUp(){
        variableExpenses = new VariableExpenses();
        chartData = new ChartData();

        dateFrom = LocalDate.of(2020,01,01);
        dateTo = LocalDate.now();
        categories = VariableExpenses.getCategoriesNames(variableExpenses);
    }

    @Test
    void expenseCategoriesWithDailyTotalValues(){

        HashMap<String, LinkedHashMap<LocalDate, Double>> mapHashMap = ChartData.expenseCategoriesWithDailyTotalValues(dateFrom,dateTo,categories);
        List<XYChart.Series> listOfSeries = new ArrayList<>();
        for(String category:mapHashMap.keySet()){
            LinkedHashMap<LocalDate, Double> dateDoubleHashMap = mapHashMap.get(category);
            XYChart.Series series = new XYChart.Series();
            series.setName(category);
            for(LocalDate localDate:dateDoubleHashMap.keySet()){
                System.out.println("Category: "+ category +" Date: "+ localDate.toString()+ " Daily total: "+dateDoubleHashMap.get(localDate));
                series.getData().add( new XYChart.Data(localDate.toString(),dateDoubleHashMap.get(localDate)));
            }
            listOfSeries.add(series);
        }


    }

    @Test
    void  getListOfDatesInSpecificTime() {
      chartData.getListOfDatesInSpecificTime(LocalDate.now(),LocalDate.of(2020,06,30)).forEach(e-> System.out.println(e));

    }






}