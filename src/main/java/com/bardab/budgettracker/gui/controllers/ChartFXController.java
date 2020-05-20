package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.gui.ChartData;
import com.bardab.budgettracker.model.categories.Categories;
import com.bardab.budgettracker.model.categories.VariableExpenses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ListView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChartFXController {

    private MainWindowFXController mainController;

    @FXML
    private PieChart categoriesPieChart;

    @FXML
    private CategoryAxis xAxis = new CategoryAxis();
    @FXML
    private NumberAxis yAxis = new NumberAxis();
    @FXML
    private LineChart<String, Double> lineChart;

    @FXML
    private ListView<String> categoriesListView = new ListView();


    private ObservableList categoriesList;
    private VariableExpenses variableExpenses;


    public void init(MainWindowFXController mainController) {
        this.mainController = mainController;
        variableExpenses = new VariableExpenses();
        variableExpenses.initializeFields(variableExpenses);
        categoriesList = FXCollections.observableArrayList((VariableExpenses.getPresentableCategoriesNames(variableExpenses)));
        categoriesListView.setItems(categoriesList);


    }


    public void updatePieChart(LocalDate dateFrom, LocalDate dateTo, List<String> listOfCategories){
        List<String> camelCaseCategories = new ArrayList();
        for (String category : listOfCategories) {
            camelCaseCategories.add(Categories.transformToCamelCase(category));
        }
        listOfCategories = FXCollections.observableArrayList(camelCaseCategories);

        this.categoriesPieChart.setData(ChartData.getSeriesForPieChart(dateFrom,dateTo,listOfCategories));

    }

    public void updateLineChart(LocalDate dateFrom, LocalDate dateTo, List<String> listOfCategories) {

        List<String> camelCaseCategories = new ArrayList();
        for (String category : listOfCategories) {
            camelCaseCategories.add(Categories.transformToCamelCase(category));
        }
        listOfCategories = FXCollections.observableArrayList(camelCaseCategories);

        List<XYChart.Series<String, Double>> list = ChartData.getListOfSeriesForLineChart(dateFrom, dateTo, listOfCategories);

        lineChart.getData().setAll(list);
    }

}
