package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.dao.TransactionDao;
import com.bardab.budgettracker.gui.additional.DoubleFormatter;
import com.bardab.budgettracker.model.Transaction;
import com.bardab.budgettracker.model.additional.CategoryFormatter;
import com.bardab.budgettracker.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class NewTransactionFXController {

    private TransactionDao transactionDao;

    private MainWindowFXController mainWindowFXController;
    public NewTransactionFXController() {
        transactionDao = new TransactionDao(HibernateUtil.getInstance().getSessionFactory());
    }

    @FXML
    private ObservableList<String> categoriesList;
    @FXML
    private ComboBox categoriesComboBox;

    @FXML
    private DatePicker newTransactionDatePicker = new DatePicker();
    @FXML
    private TextField valueField = new TextField();
    @FXML
    private TextField descriptionField = new TextField();

    public void init(MainWindowFXController mainWindowFXController){
        this.mainWindowFXController = mainWindowFXController;
        setCategoriesComboBox();
        descriptionField.clear();
        newTransactionDatePicker.setValue(LocalDate.now());
        valueField.setTextFormatter(new DoubleFormatter().doubleFormatter());

    }



    public void setCategoriesComboBox(){
        categoriesList = FXCollections.observableArrayList(CategoryFormatter.getAllCategoriesNamesInPresentable());
        categoriesComboBox.setPromptText("Categories");
        categoriesComboBox.setItems(categoriesList);
    }


    public void addTransaction(){
        Transaction transaction = new Transaction();
        transaction.setCategory( CategoryFormatter.getCategory((String) categoriesComboBox.getValue()));
        transaction.setDescription(descriptionField.getText());
        transaction.setValue((Double.parseDouble(valueField.getText())));
        transaction.setTransactionDate(newTransactionDatePicker.getValue());

        transactionDao.addTransactionAndUpdateAllFields(transaction);
        mainWindowFXController.updateTransactionsTable();
        mainWindowFXController.updateMonthlyBalance();

    }
}
