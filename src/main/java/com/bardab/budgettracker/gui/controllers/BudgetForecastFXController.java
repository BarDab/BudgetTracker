package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.dao.BudgetForecastDao;
import com.bardab.budgettracker.dao.categories.FixedCostsForecastDao;
import com.bardab.budgettracker.gui.DoubleFormatter;
import com.bardab.budgettracker.model.BudgetForecast;
import com.bardab.budgettracker.model.categories.FixedCostsForecast;
import com.bardab.budgettracker.model.additional.MonthCode;
import com.bardab.budgettracker.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BudgetForecastFXController {

    @FXML
    private ComboBox<String> yearComboBox;
    @FXML
    private ComboBox<String> monthComboBox;
    @FXML
    private ComboBox fixedSpendingComboBox;
    @FXML
    private Button fixedSpendingButton;
    @FXML
    private Button approveButton;
    @FXML
    private TextField incomeTextField = new TextField();
    @FXML
    private TextField fixedSpendingTextField = new TextField();
    @FXML
    private TextField additionalSpendingTextField = new TextField();
    @FXML
    private TextField savingGoalTextField = new TextField();
    @FXML
    private ObservableList<String> yearList = FXCollections.observableArrayList(MonthCode.yearList());
    @FXML
    private ObservableList<String> monthList = FXCollections.observableArrayList(MonthCode.monthNames());
    @FXML
    private ObservableList<String> typesList ;
    @FXML
    private Label dateWarningLabel;


    private BudgetForecastDao budgetForecastDao;
    private BudgetForecast budgetForecast;
    private FixedCostsForecastDao fixedCostsForecastDao;
    private FixedCostsForecast fixedCostsForecast;

    public BudgetForecastFXController() {
        this.budgetForecastDao = new BudgetForecastDao(HibernateUtil.getInstance().getSessionFactory());
        this.fixedCostsForecastDao = new FixedCostsForecastDao(HibernateUtil.getInstance().getSessionFactory());
    }

    public void init(){
        this.fixedCostsForecast = this.fixedCostsForecastDao.getLastFixedCostsRecord();
        setFixedCostsComboBox();
        setTextFieldsTextFormatter();
        this.monthComboBox.setItems(monthList);
        this.yearComboBox.setItems(yearList);
    }

    public void setTextFieldsTextFormatter(){
        this.incomeTextField.setTextFormatter(new DoubleFormatter().doubleFormatter());
        this.fixedSpendingTextField.setTextFormatter(new DoubleFormatter().doubleFormatter());
        this.additionalSpendingTextField.setTextFormatter(new DoubleFormatter().doubleFormatter());
        this.savingGoalTextField.setTextFormatter(new DoubleFormatter().doubleFormatter());
    }

    public void setFixedCostsComboBox(){
        this.typesList = FXCollections.observableArrayList(fixedCostsForecast.getFixedCostsTypesWithValuesList());
        this.fixedSpendingComboBox.setItems(typesList);
    }

    public void setNewBudget(String monthCode){
        budgetForecast = new BudgetForecast();
        budgetForecast.setMonthCode(monthCode);
        budgetForecast.setIncome(Double.parseDouble(incomeTextField.getText()));
        budgetForecast.setAdditionalSpending(Double.parseDouble(additionalSpendingTextField.getText()));
        budgetForecast.setSavingGoal(Double.parseDouble(savingGoalTextField.getText()));
        budgetForecast.setFixedCostsForecast(fixedCostsForecast);
        budgetForecast.setTotalFixedCosts();
        budgetForecast.setAverageDailyFundsAfterDeductionOfAllCosts();
        budgetForecast.setDailyAverageIncome();
    }

    public void setFixedCostsForecast(String monthCode){
        fixedCostsForecast.setMonthCode(monthCode);
        fixedCostsForecast.setBudgetForecast(budgetForecast);
    }

    public void approveBudget(){

        try{
            String monthCode = getMonthCode();
            setNewBudget(monthCode);
            setFixedCostsForecast(monthCode);

            this.dateWarningLabel.setVisible(false);
            this.budgetForecastDao.addTransaction(budgetForecast);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            this.dateWarningLabel.setVisible(true);
            return;
        }
    }

    public void updateFixedCost(){

        String selectedItemsName = this.fixedSpendingComboBox.getSelectionModel().getSelectedItem().toString().split(" ")[0];
        int index = this.fixedSpendingComboBox.getSelectionModel().getSelectedIndex();
        switch (selectedItemsName){
            case "electricity": fixedCostsForecast.setElectricity(Double.parseDouble(fixedSpendingTextField.getText()));
            break;
            case "entertainment": fixedCostsForecast.setEntertainment(Double.parseDouble(fixedSpendingTextField.getText()));
            break;
            case "gas": fixedCostsForecast.setGas(Double.parseDouble(fixedSpendingTextField.getText()));
            break;
            case "healthCare": fixedCostsForecast.setHealthCare(Double.parseDouble(fixedSpendingTextField.getText()));
            break;
            case "hobby": fixedCostsForecast.setHobby(Double.parseDouble(fixedSpendingTextField.getText()));
            break;
            case "otherUtilities": fixedCostsForecast.setOther(Double.parseDouble(fixedSpendingTextField.getText()));
            break;
            case "rent": fixedCostsForecast.setRent(Double.parseDouble(fixedSpendingTextField.getText()));
            break;
            case "transport": fixedCostsForecast.setTransport(Double.parseDouble(fixedSpendingTextField.getText()));
            break;
        }
        setFixedCostsComboBox();
        this.fixedSpendingComboBox.getSelectionModel().select(index);
    }

    public String getMonthCode(){
        return MonthCode.createMonthCode(this.monthComboBox.getValue(),this.yearComboBox.getValue());
    }
}
