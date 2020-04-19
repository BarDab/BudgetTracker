package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.dao.BudgetForecastDao;
import com.bardab.budgettracker.dao.FixedCostsDao;
import com.bardab.budgettracker.gui.DoubleFormatter;
import com.bardab.budgettracker.model.BudgetForecast;
import com.bardab.budgettracker.model.FixedCosts;
import com.bardab.budgettracker.model.MonthCode;
import com.bardab.budgettracker.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class BudgetForecastFXController {

    @FXML
    private ComboBox year;
    @FXML
    private ComboBox month;
    @FXML
    private TextField incomeTextField = new TextField();
    @FXML
    private ComboBox fixedSpendingComboBox;
    @FXML
    private TextField fixedSpendingTextField = new TextField();
    @FXML
    private Button fixedSpendingButton;
    @FXML
    private TextField additionalSpendingTextField = new TextField();
    @FXML
    private TextField savingGoalTextField = new TextField();
    @FXML
    private Button approveButton;
    @FXML
    ObservableList<String> yearList = FXCollections.observableArrayList(MonthCode.yearList);
    @FXML
    ObservableList<String> monthList = FXCollections.observableArrayList(MonthCode.monthNames());
    @FXML
    ObservableList<String> typesList ;
    @FXML
    private Label dateWarningLabel;



    private BudgetForecastDao budgetForecastDao;
    private FixedCostsDao fixedCostsDao;
    private FixedCosts fixedCosts;

    public BudgetForecastFXController() {
    }

    public void init(){
        this.budgetForecastDao = new BudgetForecastDao(HibernateUtil.getInstance().getSessionFactory());
        this.fixedCostsDao = new FixedCostsDao(HibernateUtil.getInstance().getSessionFactory());
        this.fixedCosts = this.fixedCostsDao.getLastFixedCostsRecord();


        typesList = FXCollections.observableArrayList(fixedCosts.getFixedCostsTypesWithValuesList());
        this.fixedSpendingComboBox.setItems(typesList);


        this.incomeTextField.setTextFormatter(new DoubleFormatter().doubleFormatter());
        this.fixedSpendingTextField.setTextFormatter(new DoubleFormatter().doubleFormatter());
        this.fixedSpendingTextField.setText(this.fixedSpendingComboBox.getSelectionModel().toString().split(" ")[this.fixedSpendingComboBox.
                getSelectionModel().toString().split(" ").length-1]);
        this.additionalSpendingTextField.setTextFormatter(new DoubleFormatter().doubleFormatter());
        this.savingGoalTextField.setTextFormatter(new DoubleFormatter().doubleFormatter());
        this.month.setItems(monthList);
        this.year.setItems(yearList);
    }

    public void approveBudget(){


        try{
            fixedCosts.setMonthCode(getMonthCode());
            BudgetForecast budgetForecast = new BudgetForecast();

            fixedCosts.setBudgetForecast(budgetForecast);
            budgetForecast.setFixedCosts(fixedCosts);

            budgetForecast.setMonthCode(getMonthCode());
            budgetForecast.setIncome(Double.parseDouble(incomeTextField.getText()));
            budgetForecast.setAdditionalSpending(Double.parseDouble(additionalSpendingTextField.getText()));
            budgetForecast.setSavingGoal(Double.parseDouble(savingGoalTextField.getText()));
            budgetForecast.setIncomeLeftForDailySpending();
            budgetForecast.setDailyAverageIncome();

            this.dateWarningLabel.setVisible(false);
            this.fixedCostsDao.addTransaction(fixedCosts);
            this.budgetForecastDao.addTransaction(budgetForecast);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            this.dateWarningLabel.setVisible(true);
            return;
        }
    }

    public void updateFixedCost(){
        Double.parseDouble(fixedSpendingTextField.getText());

        String selectedItem = this.fixedSpendingComboBox.getSelectionModel().getSelectedItem().toString().split(" ")[0];
        int index = this.fixedSpendingComboBox.getSelectionModel().getSelectedIndex();
        switch (selectedItem){
            case "electricity": fixedCosts.setElectricity(Double.parseDouble(fixedSpendingTextField.getText()));
            break;
            case "entertainment": fixedCosts.setEntertainment(Double.parseDouble(fixedSpendingTextField.getText()));
            break;
            case "gas": fixedCosts.setGas(Double.parseDouble(fixedSpendingTextField.getText()));
            break;
            case "healthCare": fixedCosts.setHealthCare(Double.parseDouble(fixedSpendingTextField.getText()));
            break;
            case "hobby": fixedCosts.setHobby(Double.parseDouble(fixedSpendingTextField.getText()));
            break;
            case "otherUtilities": fixedCosts.setOtherUtilities(Double.parseDouble(fixedSpendingTextField.getText()));
            break;
            case "rent": fixedCosts.setRent(Double.parseDouble(fixedSpendingTextField.getText()));
            break;
            case "transport": fixedCosts.setTransport(Double.parseDouble(fixedSpendingTextField.getText()));
            break;
        }

        typesList = FXCollections.observableArrayList(fixedCosts.getFixedCostsTypesWithValuesList());
        this.fixedSpendingComboBox.setItems(typesList);
        this.fixedSpendingComboBox.getSelectionModel().select(index);


    }


    public String getMonthCode(){
        return MonthCode.createMonthCode(this.month.getValue().toString(),this.year.getValue().toString());
    }













}
