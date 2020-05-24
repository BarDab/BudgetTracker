package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.model.MonthlyBalance;
import com.bardab.budgettracker.model.categories.Categories;
import com.bardab.budgettracker.model.categories.FixedExpenses;
import com.bardab.budgettracker.model.categories.VariableExpenses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;


public class ChartDataFormFXController {

    @FXML
    private static final List<String> fixedExpensesList = Categories.getPresentableCategoriesNames(new FixedExpenses());
    private static final List<String> variableExpensesList = Categories.getPresentableCategoriesNames(new VariableExpenses());
    private static final List<String> monthlyBalanceList = Categories.getPresentableCategoriesNames(new MonthlyBalance());

    private static final String fixedExpensesName = "Fixed Expenses";
    private static final String variableExpensesName = "Variable Expenses";
    private static final String balanceMeasures = "Balance Measures";


    private MainWindowFXController mainController;

    @FXML
    private ListView<String> availableCategories = new ListView<>();
    @FXML
    private ListView<String> categoriesToBeAdded = new ListView<>();

    private ObservableList<String> listOfVariableExpenses = FXCollections.observableArrayList();
    private ObservableList<String> listOfFixedExpenses = FXCollections.observableArrayList();
    private ObservableList<String> listOfBalanceMeasures = FXCollections.observableArrayList();

    private ObservableList<String> listOfCategoriesToBeAdded = FXCollections.observableArrayList();

    @FXML
    private Label chosenListName;

    @FXML
    private DatePicker datePickerFrom;
    @FXML
    private DatePicker datePickerTo;


    public void init(MainWindowFXController mainController) {
        chosenListName.setText(variableExpensesName);
        this.mainController = mainController;
        populateAvailableCategories();
        setItemsInBothLists();
        availableCategories.setOnMouseClicked(e ->
                fromAvailableToToBeAdded()
        );
        categoriesToBeAdded.setOnMouseClicked(e ->
                fromToToBeAddedToAvailable()
        );
    }

    public void populateAvailableCategories() {
        listOfVariableExpenses.setAll(variableExpensesList);
        listOfFixedExpenses.setAll(fixedExpensesList);
        listOfBalanceMeasures.setAll(monthlyBalanceList);
    }

    public void fromAvailableToToBeAdded() {
        String selectedItem = availableCategories.getSelectionModel().getSelectedItem();
        String categoryName = chosenListName.getText();
        if (selectedItem != null) {
            switch (categoryName) {
                case (variableExpensesName):
                    listOfCategoriesToBeAdded.add(selectedItem);
                    listOfVariableExpenses.remove(selectedItem);
                    break;
                case (fixedExpensesName):
                    listOfCategoriesToBeAdded.add(selectedItem);
                    listOfFixedExpenses.remove(selectedItem);
                    break;
                case (balanceMeasures):
                    listOfCategoriesToBeAdded.add(selectedItem);
                    listOfBalanceMeasures.remove(selectedItem);
                    break;
            }

        }
    }

    public void fromToToBeAddedToAvailable() {
        String selectedItem = categoriesToBeAdded.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if(Categories.validateType(selectedItem,monthlyBalanceList)) {
                listOfBalanceMeasures.add(selectedItem);
            }
            else if (Categories.validateType(selectedItem,fixedExpensesList)) {
                listOfFixedExpenses.add(selectedItem);
            }
            else listOfVariableExpenses.add(selectedItem);

            listOfCategoriesToBeAdded.remove(selectedItem);
        }
    }

    public void switchList() {
        switch (chosenListName.getText()) {
            case (variableExpensesName):
                switchToFixedExpensesList();
                break;
            case (fixedExpensesName):
                switchToMeasuresList();
                break;
            case (balanceMeasures):
                switchToVariableExpensesList();
                break;

        }
    }


    public void switchToFixedExpensesList() {
        availableCategories.setItems(listOfFixedExpenses);
        this.chosenListName.setText(fixedExpensesName);
    }

    public void switchToMeasuresList() {
        availableCategories.setItems(listOfBalanceMeasures);
        this.chosenListName.setText(balanceMeasures);

    }

    public void switchToVariableExpensesList() {
        availableCategories.setItems(listOfVariableExpenses);
        this.chosenListName.setText(variableExpensesName);
    }


    public void moveAllCategoriesFromAvailable() {
        String categoryName = chosenListName.getText();
        switch (categoryName) {
            case (variableExpensesName):
                listOfCategoriesToBeAdded.addAll(listOfVariableExpenses);
                listOfVariableExpenses.removeAll(listOfVariableExpenses);
                break;
            case (fixedExpensesName):
                listOfCategoriesToBeAdded.addAll(listOfFixedExpenses);
                listOfFixedExpenses.removeAll(listOfFixedExpenses);
                break;
            case (balanceMeasures):
                listOfCategoriesToBeAdded.addAll(listOfBalanceMeasures);
                listOfBalanceMeasures.removeAll(listOfBalanceMeasures);
                break;
        }
    }

    public void moveAllCategoriesFromToBeAdded() {
        listOfCategoriesToBeAdded.removeAll(listOfCategoriesToBeAdded);
        System.out.println(listOfCategoriesToBeAdded.isEmpty());
        populateAvailableCategories();
    }


    public void setItemsInBothLists() {
        availableCategories.setItems(listOfVariableExpenses);
        categoriesToBeAdded.setItems(listOfCategoriesToBeAdded);

    }

    public void updatePieChart() {
        this.mainController.updatePieChart(datePickerFrom.getValue(), datePickerTo.getValue(), listOfCategoriesToBeAdded);
    }

    public void updateLineChart() {
        this.mainController.updateLineChart(datePickerFrom.getValue(), datePickerTo.getValue(), listOfCategoriesToBeAdded);
    }
}
