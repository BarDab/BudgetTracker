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
    private FixedCostsDao fixedCostsDao;
    private FixedCosts fixedCosts;

    public BudgetForecastFXController() {
        this.budgetForecastDao = new BudgetForecastDao(HibernateUtil.getInstance().getSessionFactory());
        this.fixedCostsDao = new FixedCostsDao(HibernateUtil.getInstance().getSessionFactory());
    }

    public void init(){
        this.fixedCosts = this.fixedCostsDao.getLastFixedCostsRecord();
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
        this.typesList = FXCollections.observableArrayList(fixedCosts.getFixedCostsTypesWithValuesList());
        this.fixedSpendingComboBox.setItems(typesList);
    }

    public void setNewBudget(String monthCode){
        budgetForecast = new BudgetForecast();
        budgetForecast.setMonthCode(monthCode);
        budgetForecast.setIncome(Double.parseDouble(incomeTextField.getText()));
        budgetForecast.setAdditionalSpending(Double.parseDouble(additionalSpendingTextField.getText()));
        budgetForecast.setSavingGoal(Double.parseDouble(savingGoalTextField.getText()));
        budgetForecast.setFixedCosts(fixedCosts);
        budgetForecast.setTotalFixedCosts();
        budgetForecast.setAverageDailyFundsAfterDeductionOfAllCosts();
        budgetForecast.setDailyAverageIncome();
    }

    public void setFixedCosts(String monthCode){
        fixedCosts.setMonthCode(monthCode);
        fixedCosts.setBudgetForecast(budgetForecast);
    }

    public void approveBudget(){

        try{
            String monthCode = getMonthCode();
            setNewBudget(monthCode);
            setFixedCosts(monthCode);

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
        setFixedCostsComboBox();
        this.fixedSpendingComboBox.getSelectionModel().select(index);
    }

    public String getMonthCode(){
        return MonthCode.createMonthCode(this.monthComboBox.getValue(),this.yearComboBox.getValue());
    }
}
