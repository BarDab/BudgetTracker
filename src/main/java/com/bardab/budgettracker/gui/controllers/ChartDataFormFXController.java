package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.gui.ChartData;
import com.bardab.budgettracker.model.categories.Categories;
import com.bardab.budgettracker.model.categories.FixedExpenses;
import com.bardab.budgettracker.model.categories.VariableExpenses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;


import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ChartDataFormFXController {



    private MainWindowFXController mainController;

    @FXML
    private ListView<String> availableCategories= new ListView<>();
    @FXML
    private ListView<String> categoriesToBeAdded= new ListView<>();

    private ObservableList<String> listOfAvailableCategories = FXCollections.observableArrayList();
    private ObservableList<String> listOfCategoriesToBeAdded = FXCollections.observableArrayList();

    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button saveNewCategoryButton;
    @FXML
    private Button updateChartButton ;

    @FXML
    private DatePicker datePickerFrom;
    @FXML
    private DatePicker datePickerTo;


    public void init(MainWindowFXController mainController){
        this.mainController = mainController;
        populateAvailableCategories();
        availableCategories.setItems(listOfAvailableCategories);
        categoriesToBeAdded.setItems(listOfCategoriesToBeAdded);

    }

    public void populateAvailableCategories() {
        VariableExpenses variableExpenses = new VariableExpenses();
        FixedExpenses fixedExpenses = new FixedExpenses();
        listOfAvailableCategories.addAll(Categories.getPresentableCategoriesNames(variableExpenses));
        listOfAvailableCategories.addAll(Categories.getPresentableCategoriesNames(fixedExpenses));
    }

    public void addCategory() {
        String selectedItem = availableCategories.getSelectionModel().getSelectedItem();
        if(selectedItem!=null) {
            listOfCategoriesToBeAdded.add(selectedItem);
            listOfAvailableCategories.remove(selectedItem);
        }
    }

    public void removeCategory() {
        String selectedItem = categoriesToBeAdded.getSelectionModel().getSelectedItem();
        if(selectedItem!=null) {
            listOfAvailableCategories.add(selectedItem);
            listOfCategoriesToBeAdded.remove(selectedItem);
        }
    }

    public void saveAsNewCategory() {
    }

        public void updatePieChart() {
            this.mainController.updatePieChart(datePickerFrom.getValue(),datePickerTo.getValue(),listOfCategoriesToBeAdded);
    }

    public void updateLineChart() {
        this.mainController.updateLineChart(datePickerFrom.getValue(),datePickerTo.getValue(),listOfCategoriesToBeAdded);
    }
}
