package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.model.Budget;
import com.bardab.budgettracker.model.BudgetMonitor;
import com.bardab.budgettracker.model.MonthlyBalance;
import com.bardab.budgettracker.model.categories.Categories;
import com.bardab.budgettracker.model.categories.FixedExpenses;
import com.bardab.budgettracker.model.categories.VariableExpenses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class BalanceFXController {

//    private MonthlyBalanceDao monthlyBalanceDao ;
//    private BudgetDao budgetDao;
    private Budget budget;
    private FixedExpenses fixedExpenses;
    private VariableExpenses variableExpenses;
    private MonthlyBalance monthlyBalance;
    private Integer monthCode;


    @FXML
    private ListView<String> summaryNamesListView = new ListView<String>();
    @FXML
    private ListView<String> balanceListView = new ListView<String>();
    @FXML
    private ListView<String> budgetListView = new ListView<String>();
    @FXML
    private ListView<String> comparisonListView = new ListView<String>();
    @FXML
    private Label dateLabel = new Label();

    private ArrayList<String> summarizeNamesOrder = Categories.getCategoriesNames(new MonthlyBalance());
    private ObservableList<String> summarizeNames;
    private ObservableList<String> balanceSummarizeValues;
    private ObservableList<String> budgetSummarizeValues;
    private ObservableList<String> summarizeComparison;
    private ObservableList<String> totalFixed ;
    private ObservableList<String> totalVariable;




    public void init (Integer monthCode){
//        this.monthCode = monthCode;
//        fetchData(monthCode);
//        populateLists();
//        this.summaryNamesListView.setItems(summarizeNames);
//        this.balanceListView.setItems(balanceSummarizeValues);
//        this.budgetListView.setItems(budgetSummarizeValues);
//        this.comparisonListView.setItems(summarizeComparison);
//
//
//
//        dateLabel.setText(MonthCode.monthYear(this.monthCode));

    }
//
//    public void fetchData(Integer monthCode){
//        monthlyBalanceDao = new MonthlyBalanceDao(HibernateUtil.getInstance().getSessionFactory());
//        budgetDao = new BudgetDao(HibernateUtil.getInstance().getSessionFactory());
//
//        try {
//            monthlyBalance = monthlyBalanceDao.findByMonthCode(monthCode);
//            fixedExpenses = monthlyBalance.getFixedExpenses();
//            variableExpenses = monthlyBalance.getVariableExpenses();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            fixedExpenses = new FixedExpenses();
//            variableExpenses = new VariableExpenses();
//            monthlyBalance = new MonthlyBalance();
//            variableExpenses.initializeFields(variableExpenses);
//            fixedExpenses.initializeFields(fixedExpenses);
//        }
//        budget =  budgetDao.findByMonthCode(monthCode);
//        if(budget==null){
//            budget = new Budget();
//        }
//
//    }

    public void populateLists(){
        totalFixed = FXCollections.observableArrayList(Categories.getPresentableCategoriesWithValues(fixedExpenses));
        totalVariable = FXCollections.observableArrayList(Categories.getPresentableCategoriesWithValues(variableExpenses));
        summarizeNames = FXCollections.observableArrayList(Categories.getPresentableCategoriesNames(monthlyBalance));
        balanceSummarizeValues = FXCollections.observableArrayList(getSummarizedValuesInOrder(monthlyBalance));
        budgetSummarizeValues = FXCollections.observableArrayList(getSummarizedValuesInOrder(budget));
        summarizeComparison = FXCollections.observableArrayList(matchTheOrder(BudgetMonitor.comparedValues(monthlyBalance,budget)));
    }

    public void nextMonth(){
//        init(MonthCode.getNextMonthCode(this.monthCode));

    }
    public void previousMonth(){
//        init(MonthCode.getPreviousMonthCode(this.monthCode));
    }


    public ArrayList getSummarizedValuesInOrder(Object object){
        ArrayList<String> values = new ArrayList<>();
        summarizeNamesOrder.forEach(e->values.add(Categories.getMapOfCategories(object).get(e).toString()));
        return values;
    }

    public ArrayList<String> matchTheOrder(HashMap<String,Double> map){
        ArrayList<String> values = new ArrayList<>();
        for(String name:summarizeNamesOrder){
            if(map.get(name).isInfinite()){
                values.add("-");
            }
            else
            values.add(NumberFormat.getPercentInstance().format(map.get(name)));
        }
        return values;

    }


    }





