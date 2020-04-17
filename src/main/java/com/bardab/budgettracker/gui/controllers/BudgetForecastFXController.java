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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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




    private BudgetForecastDao budgetForecastDao;
    private FixedCostsDao fixedCostsDao;

    public BudgetForecastFXController() {
    }

    public void init(){
        this.budgetForecastDao = new BudgetForecastDao(HibernateUtil.getInstance().getSessionFactory());
        this.fixedCostsDao = new FixedCostsDao(HibernateUtil.getInstance().getSessionFactory());
        typesList = FXCollections.observableArrayList(typesListWithValues());
        this.fixedSpendingComboBox.setItems(typesList);

//        this.fixedCostsDao.getFixedCostsTypes();

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
        BudgetForecast budgetForecast = new BudgetForecast();
        budgetForecast.setMonthCode(getMonthCode());
        budgetForecast.setIncome(Double.parseDouble(incomeTextField.getText()));
        budgetForecast.setFixedSpending(Double.parseDouble(fixedSpendingTextField.getText()));
        budgetForecast.setAdditionalSpending(Double.parseDouble(additionalSpendingTextField.getText()));
        budgetForecast.setSavingGoal(Double.parseDouble(savingGoalTextField.getText()));
        budgetForecast.setIncomeLeftForDailySpending();
        budgetForecast.setDailyAverageIncome();

        this.budgetForecastDao.addTransaction(budgetForecast);
    }

    public FixedCosts setFixedCost(){
        FixedCosts fixedCosts = this.fixedCostsDao.getLastRecord();

        Double.parseDouble(fixedSpendingTextField.getText());
        switch (this.fixedSpendingComboBox.getSelectionModel().getSelectedItem().toString().split(" ")[0]){
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
        }
        this.fixedCostsDao.addTransaction(fixedCosts);
        return fixedCosts;
    }

    public List<String> typesListWithValues(){
        List<String> typesListWithValues = new ArrayList<>();
        this.fixedCostsDao.getFixedCostsTypesWithValues().entrySet().forEach(e->typesListWithValues.add(e.getKey()+" "+e.getValue()));
        return typesListWithValues;
    }

    public String getMonthCode(){
        return MonthCode.createMonthCode(this.month.getValue().toString(),this.year.getValue().toString());
    }













}
