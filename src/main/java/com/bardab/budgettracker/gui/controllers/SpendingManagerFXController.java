package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.dao.SpendingManagerDao;
import com.bardab.budgettracker.gui.additional.DoubleFormatter;
import com.bardab.budgettracker.gui.additional.IntegerFormatter;
import com.bardab.budgettracker.model.SpendingManager;
import com.bardab.budgettracker.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.YearMonth;

public class SpendingManagerFXController {

    private SpendingManager spendingManager;
    private SpendingManagerDao spendingManagerDao = new SpendingManagerDao(HibernateUtil.getInstance().getSessionFactory());




    @FXML
    private ListView spendingManagerNamesListView;

    @FXML
    private ListView spendingManagerValuesListView;

    private ObservableList<String> spendingManagerNamesObservableList = FXCollections.observableArrayList();

    private ObservableList<Double> spendingManagerValuesObservableList = FXCollections.observableArrayList();

    @FXML
    private Button saveValuesButton;
    @FXML
    private TextField cashAtHandTextField;
    @FXML
    private TextField savingsTextField;
    @FXML
    private TextField daysToNextPaymentTextField;



    public void init(){
        this.spendingManager = this.spendingManagerDao.findByYearMonth(YearMonth.now());
        if(this.spendingManager==null){
            this.spendingManager = new SpendingManager();
            this.spendingManager.initializeFields();
        }
        populateSpendingManagerNamesObservableList();
        populateSpendingManagerValuesObservableList();
        spendingManagerNamesObservableListIntoListView();
        spendingManagerValuesObservableListIntoListView();
        setFormattersForTextFields();

    }


    public void spendingManagerNamesObservableListIntoListView(){
        this.spendingManagerNamesListView.setItems(this.spendingManagerNamesObservableList);
    }
    public void spendingManagerValuesObservableListIntoListView(){
        this.spendingManagerValuesListView.setItems(this.spendingManagerValuesObservableList);
    }

    public void populateSpendingManagerNamesObservableList(){

        this.spendingManagerNamesObservableList.addAll(this.spendingManager.getMapOfCategoriesWithValues().keySet());
    }
    public void populateSpendingManagerValuesObservableList(){
        this.spendingManagerValuesObservableList.addAll(this.spendingManager.getMapOfCategoriesWithValues().values());
    }

    public void setFormattersForTextFields(){
        this.cashAtHandTextField.setTextFormatter(new DoubleFormatter().doubleFormatter());
        this.savingsTextField.setTextFormatter(new DoubleFormatter().doubleFormatter());
        this.daysToNextPaymentTextField.setTextFormatter(new IntegerFormatter().integerFormatter());
    }


}
