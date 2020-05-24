package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.dao.TransactionInsertionManager;
import com.bardab.budgettracker.gui.DoubleFormatter;
import com.bardab.budgettracker.model.Transaction;
import com.bardab.budgettracker.model.categories.FixedExpenses;
import com.bardab.budgettracker.model.categories.VariableExpenses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.List;

public class NewTransactionFXController {

    private TransactionInsertionManager transactionInsertionManager;

    public NewTransactionFXController() {
        transactionInsertionManager = new TransactionInsertionManager();
    }

    private int categoryNumber = 0;
    @FXML
    private ObservableList<String> categoriesList;
    @FXML
    private ComboBox categoriesComboBox;

    @FXML
    private DatePicker datePicker = new DatePicker();
    @FXML
    private TextField valueField = new TextField();
    @FXML
    private TextField descriptionField = new TextField();

    public void init(){
        setCategory(categoryNumber);
        descriptionField.clear();
        datePicker.setValue(LocalDate.now());
        valueField.setTextFormatter(new DoubleFormatter().doubleFormatter());
    }



    public void setVariableCategoriesComboBox(){
        VariableExpenses variableExpenses = new VariableExpenses();
        categoriesList = FXCollections.observableArrayList(variableExpenses.getPresentableCategoriesNames(variableExpenses));
        categoriesComboBox.setPromptText("Variable");
        categoriesComboBox.setItems(categoriesList);
    }

    public void setFixedCategoriesComboBox(){
        FixedExpenses fixedExpenses = new FixedExpenses();
        categoriesList = FXCollections.observableArrayList(fixedExpenses.getPresentableCategoriesNames(fixedExpenses));
        categoriesComboBox.setPromptText("Fixed");
        categoriesComboBox.setItems(categoriesList);
    }

    public void setInvestmentsCategoriesComboBox(){
        categoriesList = FXCollections.observableArrayList(List.of("stock 1"));
        categoriesComboBox.setPromptText("Stocks");
        categoriesComboBox.setItems(categoriesList);
    }

    public void setIncomeCategoriesComboBox(){
        categoriesList = FXCollections.observableArrayList(List.of("income"));
        categoriesComboBox.setItems(categoriesList);
        categoriesComboBox.setPromptText("Income");
    }

    public void setCategory(int categoryNumber){
        switch(categoryNumber){
            case 0: setVariableCategoriesComboBox(); break;
            case 1: setFixedCategoriesComboBox(); break;
            case 2: setInvestmentsCategoriesComboBox(); break;
            case 3: setIncomeCategoriesComboBox(); break;
        }
    }

    public void nextCategory(){
        if (this.categoryNumber==3){
            this.categoryNumber=0;
        }
        else this.categoryNumber++;
        setCategory(this.categoryNumber);
    }
    public void previousCategory(){
        if (this.categoryNumber==0){
            this.categoryNumber=3;
        }
        else this.categoryNumber--;
        setCategory(this.categoryNumber);
    }

    public void updateTransactionsTableView(){

    }

    public void addTransaction(){
        Transaction transaction = new Transaction();
        transaction.setCategoryAndTransformToCamelCase((String) categoriesComboBox.getValue());
        transaction.setDescription(descriptionField.getText());
        transaction.setValue((Double.parseDouble(valueField.getText())));
        transaction.setTransactionDateAndMonthCode(datePicker.getValue());

        transactionInsertionManager.insertTransactionAndUpdateActiveCategories(transaction);
        init();

    }
}
