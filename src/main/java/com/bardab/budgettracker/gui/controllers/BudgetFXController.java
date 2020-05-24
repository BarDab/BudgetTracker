package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.dao.BudgetDao;
import com.bardab.budgettracker.dao.categories.PlannedExpensesDao;
import com.bardab.budgettracker.gui.DoubleFormatter;
import com.bardab.budgettracker.model.Budget;
import com.bardab.budgettracker.model.additional.MonthCode;
import com.bardab.budgettracker.model.categories.PlannedExpenses;
import com.bardab.budgettracker.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class BudgetFXController {

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


    private BudgetDao budgetDao;
    private Budget newBudget;
    private PlannedExpensesDao plannedExpensesDao;
    private PlannedExpenses plannedExpenses;

    public BudgetFXController() {
        this.budgetDao = new BudgetDao(HibernateUtil.getInstance().getSessionFactory());
        this.plannedExpensesDao = new PlannedExpensesDao(HibernateUtil.getInstance().getSessionFactory());
    }

    public void init(){
        if(this.plannedExpensesDao.getLastFixedCostsRecord()!=null){
            this.plannedExpenses = this.plannedExpensesDao.getLastFixedCostsRecord();
        }
        else
            this.plannedExpenses = new PlannedExpenses();

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
        typesList = FXCollections.observableArrayList(types());
        this.fixedSpendingComboBox.setItems(typesList);
    }

    public void setNewBudget(Integer monthCode){
        newBudget = new Budget();
        newBudget.setMonthCode(monthCode);
        newBudget.setIncome(Double.parseDouble(incomeTextField.getText()));
        newBudget.setTotalVariableExpenses(Double.parseDouble(additionalSpendingTextField.getText()));
        newBudget.setInvestments(Double.parseDouble(savingGoalTextField.getText()));
        newBudget.setPlannedExpenses(plannedExpenses);
        newBudget.setTotalSpending();
    }

    public void setPlannedExpenses(Integer monthCode){
        plannedExpenses.setMonthCode(monthCode);
        plannedExpenses.setBudget(newBudget);
    }

    public void approveBudget(){

        try{
            Integer monthCode = getMonthCode();
            setNewBudget(monthCode);
            setPlannedExpenses(monthCode);

            this.dateWarningLabel.setVisible(false);
            this.budgetDao.addEntity(newBudget);
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
        plannedExpenses.updateValue(plannedExpenses,selectedItemsName,Double.parseDouble(fixedSpendingTextField.getText()));
        setFixedCostsComboBox();
        this.fixedSpendingComboBox.getSelectionModel().select(index);
    }

    public Integer getMonthCode(){
        String monthCode =  this.yearComboBox.getValue()+ MonthCode.getNumberOfMonth(this.monthComboBox.getValue());
        return Integer.parseInt(monthCode);
    }

    public List<String> types(){
        ArrayList<String> arrayList = new ArrayList<>();
        plannedExpenses.getMapOfCategories(plannedExpenses).forEach((e,f)-> arrayList.add(e+" "+f));
        return arrayList;

    }

}
