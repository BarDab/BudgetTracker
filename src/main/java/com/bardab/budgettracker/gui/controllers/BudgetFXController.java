package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.dao.BudgetDao;
import com.bardab.budgettracker.dao.MonthlyBalanceDao;
import com.bardab.budgettracker.dao.TransactionInsertionManager;
import com.bardab.budgettracker.gui.additional.DoubleFormatter;
import com.bardab.budgettracker.gui.additional.MonthCode;
import com.bardab.budgettracker.model.Actual;
import com.bardab.budgettracker.model.Budget;
import com.bardab.budgettracker.model.ExpenseOverrun;
import com.bardab.budgettracker.model.additional.Category;
import com.bardab.budgettracker.model.additional.CategoryFormatter;
import com.bardab.budgettracker.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BudgetFXController {

    private MonthlyBalanceDao monthlyBalanceDao;
    private BudgetDao budgetDao = new BudgetDao(HibernateUtil.getInstance().getSessionFactory());
    private Actual actual;

    private Budget budget;
    private ExpenseOverrun expenseOverrun;
    private YearMonth yearMonth;
    private MainWindowFXController mainController;


    @FXML
    private ObservableList<String> yearList = FXCollections.observableArrayList(MonthCode.yearList());
    @FXML
    private ObservableList<String> monthList = FXCollections.observableArrayList(MonthCode.monthNames());

    @FXML
    private ComboBox<String> yearComboBox;
    @FXML
    private ComboBox<String> monthComboBox;


    @FXML
    private ListView<String> actualIncomeSavingsListView = new ListView<String>();
    @FXML
    private ListView<String> budgetIncomeSavingsListView = new ListView<String>();

    @FXML
    private Button newBudgetButton;
    @FXML
    private Button saveUpdateBudgetButton;





    @FXML
    private ListView<String> incomeSavingsNamesListView = new ListView<String>();
    @FXML
    private ListView<String> expensesNamesListView = new ListView<String>();
    @FXML
    private ListView<String> budgetExpensesListView = new ListView<String>();
    @FXML
    private ListView<String> actualExpensesListView = new ListView<String>();
    @FXML
    private ListView<String> expensesOverrunListView = new ListView<String>();



    private ObservableList<String> observableIncomeSavingsNamesList = FXCollections.observableArrayList();
    private ObservableList<String> observableExpensesNamesList = FXCollections.observableArrayList();

    private ObservableList<String> actualIncomeSavingsObservableList = FXCollections.observableArrayList();
    private ObservableList<String> actualExpensesObservableList = FXCollections.observableArrayList();


    private ObservableList<String> budgetIncomeSavingsObservableList = FXCollections.observableArrayList();
    private ObservableList<String> budgetExpensesObservableList = FXCollections.observableArrayList();

    private ObservableList<String> expenseOverrunObservableList = FXCollections.observableArrayList();




    public void update() {
        setYearMonth();
        if(getBudgetFromDB()!=true){
            this.budget = TransactionInsertionManager.initializeBudget(this.yearMonth);
            System.out.println(this.budget.toString());
        };
        getActualFromDB();
        setExpensesOverrun(actual, budget);




        populateAllLists();

//        showData();
    }


    public void init(MainWindowFXController mainController) {
        this.mainController = mainController;

        initializeYearComboBox();
        initializeMonthComboBox();
        setYearMonth();

        getBudgetFromDB();
        getActualFromDB();
        setExpensesOverrun(actual, budget);



        populateAllLists();
        setListViewsPlaceHolders();

    }


    public void newBudget(){

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(this.mainController.getMainPane().getScene().getWindow());
        dialog.setTitle("New Budget");


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/bardab/budgettracker/gui/fxmls/newBudget.fxml"));



        try {
            System.out.println("inside try new budget");
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }
        catch (IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);


        Optional<ButtonType> result = dialog.showAndWait();

    }


    public void setYearMonth() {
        Integer year = Integer.parseInt(this.yearComboBox.getSelectionModel().getSelectedItem());
        Integer month = MonthCode.monthNames().indexOf(this.monthComboBox.getSelectionModel().getSelectedItem()) + 1;
        this.yearMonth = YearMonth.of(year, month);
    }

    public void initializeYearComboBox() {
        String year = String.valueOf(LocalDate.now().getYear());
        this.yearComboBox.setItems(yearList);
        this.yearComboBox.getSelectionModel().select(year);

    }
    public void initializeMonthComboBox() {
        String month = MonthCode.getMonthInPresentable(LocalDate.now().getMonth());
        this.monthComboBox.setItems(monthList);
        this.monthComboBox.getSelectionModel().select(month);
    }

    public void setListViewsPlaceHolders() {
        ObservableList<String> placeHolder = FXCollections.observableArrayList();
        placeHolder.add("");
        if(this.budgetExpensesObservableList.isEmpty()){
            this.budgetIncomeSavingsListView.setPlaceholder(new ListView<>(placeHolder));
        }


    }

    public void populateAllLists() {
        populateIncomeSavingsNamesObservableList();
        populateExpensesNamesObservableList();

        populateBudgetIncomeSavingsObservableList(budget);
        populateBudgetExpensesObservableList(budget);
        populateActualIncomeSavingsObservableList(actual);
        populateActualExpensesObservableList(actual);

        populateExpensesOverrunObservableList(expenseOverrun);



    }
    public void populateIncomeSavingsNamesObservableList() {
        List<String> categoriesNames = new ArrayList<>();
        categoriesNames.add(CategoryFormatter.getCategoryNameInPresentable(Category.INCOME));
        categoriesNames.add(CategoryFormatter.getCategoryNameInPresentable(Category.SAVINGS));

        this.observableIncomeSavingsNamesList = FXCollections.observableArrayList(categoriesNames);
        this.incomeSavingsNamesListView.setItems(observableIncomeSavingsNamesList);
    }
    public void populateExpensesNamesObservableList() {
        List<String> categoriesNames = new ArrayList<>();

        categoriesNames.addAll(CategoryFormatter.getExpenseCategoriesNamesInPresentable());
        this.observableExpensesNamesList = FXCollections.observableArrayList(categoriesNames);
        this.expensesNamesListView.setItems(observableExpensesNamesList);
    }

    public void populateBudgetIncomeSavingsObservableList(Budget budget) {
        List<String> budgetValues = new ArrayList<>();
        if (this.budget != null) {
            budgetValues.add(budget.getBudgetIncome().getCategoryValue(Category.INCOME).toString());
            budgetValues.add(budget.getBudgetSavings().getCategoryValue(Category.SAVINGS).toString());
        }
        this.budgetIncomeSavingsObservableList = FXCollections.observableArrayList(budgetValues);
        this.budgetIncomeSavingsListView.setItems(budgetIncomeSavingsObservableList);

    }
    public void populateBudgetExpensesObservableList(Budget budget) {
        List<String> budgetValues = new ArrayList<>();
        if (this.budget != null) {
            budgetValues.add(budget.getTotalExpenses().toString());
            budgetValues.addAll(
                    DoubleFormatter.doubleListToStringList(new ArrayList<>(budget.getBudgetExpenses().getMapOfCategoriesWithValues().values())));
        }
        this.budgetExpensesObservableList = FXCollections.observableArrayList(budgetValues);
        this.budgetExpensesListView.setItems(budgetExpensesObservableList);
    }
    public void populateActualIncomeSavingsObservableList(Actual actual) {
        List<String> actualValues = new ArrayList<>();
        if (this.actual != null) {
            actualValues.add(actual.getActualIncome().getCategoryValue(Category.INCOME).toString());
            actualValues.add(actual.getActualSavings().getCategoryValue(Category.SAVINGS).toString());
        }
        this.actualIncomeSavingsObservableList = FXCollections.observableArrayList(actualValues);
        this.actualIncomeSavingsListView.setItems(actualIncomeSavingsObservableList);
    }
    public void populateActualExpensesObservableList(Actual actual) {
        List<String> actualValues = new ArrayList<>();
        if (this.actual != null) {
            actualValues.add(actual.getTotalExpenses().toString());
            actualValues.addAll(
                    DoubleFormatter.doubleListToStringList(new ArrayList<>(actual.getActualExpenses().getMapOfCategoriesWithValues().values())));
        }
        this.actualExpensesObservableList = FXCollections.observableArrayList(actualValues);
        this.actualExpensesListView.setItems(actualExpensesObservableList);
    }
    public void populateExpensesOverrunObservableList(ExpenseOverrun overrun) {
        List<String> expenseOverrun = new ArrayList<>();
        overrun.initializeCategoryValues();
        if (overrun!= null) {
            expenseOverrun.add(overrun.getTotalExpenses().toString());
            expenseOverrun.addAll(
                    DoubleFormatter.doubleListToStringList(new ArrayList<>(overrun.getMapOfCategoriesWithValues().values())));
        }
        this.expenseOverrunObservableList = FXCollections.observableArrayList(expenseOverrun);
        this.expensesOverrunListView.setItems(expenseOverrunObservableList);
    }












    private void setExpensesOverrun(Actual actual, Budget previousBudget) {
        this.expenseOverrun = TransactionInsertionManager.setOverrun(actual, previousBudget);
    }
    private void getActualFromDB() {
        monthlyBalanceDao = new MonthlyBalanceDao(HibernateUtil.getInstance().getSessionFactory());
        try {
            this.actual = this.monthlyBalanceDao.findByYearMonth(yearMonth);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean getBudgetFromDB() {
        budgetDao = new BudgetDao(HibernateUtil.getInstance().getSessionFactory());
        try {
            this.budget = budgetDao.findByYearMonth(yearMonth);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }






    }










