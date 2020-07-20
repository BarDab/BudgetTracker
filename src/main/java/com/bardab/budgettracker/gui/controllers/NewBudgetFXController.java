package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.dao.BudgetDao;
import com.bardab.budgettracker.dao.TransactionInsertionManager;
import com.bardab.budgettracker.gui.additional.DoubleFormatter;
import com.bardab.budgettracker.gui.additional.MonthCode;
import com.bardab.budgettracker.gui.additional.NewBudgetTableData;
import com.bardab.budgettracker.model.Budget;
import com.bardab.budgettracker.model.additional.Category;
import com.bardab.budgettracker.model.additional.CategoryFormatter;
import com.bardab.budgettracker.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class NewBudgetFXController {

    private BudgetDao budgetDao = new BudgetDao(HibernateUtil.getInstance().getSessionFactory());

    @FXML
    private DialogPane newBudgetPane;

    @FXML
    private GridPane gridPane;


    private Pane mainWindow;

    @FXML
    private TableView newBudgetIncomeSavingsTableView;

    @FXML
    private TableColumn newBudgetIncomeSavingsTableColumn;

    @FXML
    private TableView newBudgetExpensesTableView;
    @FXML
    private TableColumn newBudgetExpensesTableColumn;

    @FXML
    private ComboBox<String> yearComboBox;
    @FXML
    private ComboBox<String> monthComboBox;


    private Budget newBudget;
    private Budget budget;

    private YearMonth yearMonth;


    private ObservableList<NewBudgetTableData> newBudgetIncomeSavingsObservableList = FXCollections.observableArrayList();
    private ObservableList<NewBudgetTableData> newBudgetExpensesObservableList = FXCollections.observableArrayList();


    public void init(Pane pane,Budget budget) {
        this.mainWindow = pane;
        this.budget = budget;
        if(budget==null){
            this.budget = TransactionInsertionManager.initializeBudget(YearMonth.now());
        }
        createGridPaneWithLabelsAndTextFields(this.gridPane,this.budget);

    }


    public void createGridPaneWithLabelsAndTextFields(GridPane gridPane, Budget budget) {
        HashMap<Label, TextField> hashMap = populateMapOfCategoryLabelsWithValuesTextFields(budget);

        for (Label label : hashMap.keySet()) {
            int column = 0;
            int row = 0;
            gridPane.add(label, column, row);
            gridPane.add(hashMap.get(label), column + 1, row);

            row++;
        }
    }

    public HashMap<Label, TextField> populateMapOfCategoryLabelsWithValuesTextFields(Budget budget) {
        LinkedHashMap<Label, TextField> namesWithValues = new LinkedHashMap<>();

        Label income = new Label(Category.INCOME.toString());
        TextField incomeValueField = new TextField();
        incomeValueField.setTextFormatter(new DoubleFormatter().doubleFormatter());
        incomeValueField.setText(budget.getBudgetIncome().getIncomeValue().toString());
        namesWithValues.put(income, incomeValueField);


        Label savings = new Label(Category.SAVINGS.toString());
        TextField savingsValueField = new TextField();
        savingsValueField.setTextFormatter(new DoubleFormatter().doubleFormatter());
        incomeValueField.setText(budget.getBudgetSavings().getSavingsValue().toString());
        namesWithValues.put(savings, savingsValueField);

        for (Category category : Category.expenses()) {
            Label label = new Label(category.toString());
            TextField textField = new TextField();
            textField.setTextFormatter(new DoubleFormatter().doubleFormatter());
            textField.setText(budget.getBudgetExpenses().getCategoryValue(category).toString());
            namesWithValues.put(label, textField);
        }
        return namesWithValues;
    }

    public HashMap<Category, Double> getDataFromTextFields(HashMap<Label, TextField> hashMap) {
        HashMap<Category,Double> categoryDoubleHashMap = new HashMap<>();
        for(Label label:hashMap.keySet()){
            categoryDoubleHashMap.put(CategoryFormatter.getCategory(label.getText()),Double.parseDouble(hashMap.get(label).getText()));
        }
        return categoryDoubleHashMap;
    }

    public Budget createNewBudget(HashMap<Category, Double> categoriesWithValues, YearMonth yearMonth) {
        Budget budget = TransactionInsertionManager.initializeBudget(yearMonth);
        for (Category category : categoriesWithValues.keySet()) {
            if (category.equals(Category.INCOME)) {
                budget.getBudgetIncome().setIncomeValue(categoriesWithValues.get(category));
            } else if (category.equals(Category.SAVINGS)) {
                budget.getBudgetSavings().setSavingsValue(categoriesWithValues.get(category));
            } else {
                budget.getBudgetExpenses().updateCategoryValue(category, categoriesWithValues.get(category));
            }
        }
        return budget;
    }

    public void addNewBudget() {

    }

    public void updateExistingBudget() {
    }


    public void setEditableNewBudgetIncomeSavingsTableColumn() {
        newBudgetIncomeSavingsTableColumn.setSortable(false);
        newBudgetIncomeSavingsTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(DoubleFormatter.converter));
        newBudgetIncomeSavingsTableColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<NewBudgetTableData, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<NewBudgetTableData, Double> t) {
                        t.getRowValue().setNewBudgetValue(t.getNewValue());
                    }
                }
        );
    }


    public void setEditableNewBudgetExpensesTableColumn() {
        newBudgetExpensesTableColumn.setSortable(false);
        newBudgetExpensesTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(DoubleFormatter.converter));
        newBudgetExpensesTableColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<NewBudgetTableData, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<NewBudgetTableData, Double> t) {

                        t.getRowValue().setNewBudgetValue(t.getNewValue());
                        setExpenseTableColumnHeaderAsTotalExpenses();
                    }
                }
        );
    }

    public void initializeNewBudgetIncomeSavingsObservableList() {
        List<NewBudgetTableData> list = new ArrayList<>();
        list.add(new NewBudgetTableData(0.0, Category.INCOME));
        list.add(new NewBudgetTableData(0.0, Category.SAVINGS));
        this.newBudgetIncomeSavingsObservableList = FXCollections.observableList(list);
    }

    public void initializeNewBudgetExpensesObservableList() {
        List<NewBudgetTableData> list = new ArrayList<>();
        for (Category category : Category.expenses()) {
            list.add(new NewBudgetTableData(0.0, category));
        }
        this.newBudgetExpensesObservableList = FXCollections.observableList(list);
    }

    public void copyAllValuesFromBudgetToNewBudget() {
        copyIncomeSavingsFromBudgetToNewBudget();
        copyExpensesFromBudgetToNewBudget();
        setExpenseTableColumnHeaderAsTotalExpenses();
    }

    public void copyExpensesFromBudgetToNewBudget() {
        List<NewBudgetTableData> expensesList = new ArrayList<>();
        for (Category expense : Category.expenses()) {
            expensesList.add(new NewBudgetTableData(budget.getBudgetExpenses().getCategoryValue(expense), expense));
            System.out.println(budget.getBudgetExpenses().getCategoryValue(expense) + "" + expense);
        }
        this.newBudgetExpensesTableView.getItems().removeAll(newBudgetExpensesObservableList);
        this.newBudgetExpensesObservableList = FXCollections.observableList(expensesList);
        this.newBudgetExpensesTableView.getItems().addAll(newBudgetExpensesObservableList);

    }

    public void copyIncomeSavingsFromBudgetToNewBudget() {
        ObservableList<NewBudgetTableData> incomeSavingsList = FXCollections.observableArrayList();
        incomeSavingsList.add(new NewBudgetTableData(budget.getBudgetIncome().getIncomeValue(), Category.INCOME));
        incomeSavingsList.add(new NewBudgetTableData(budget.getBudgetSavings().getSavingsValue(), Category.SAVINGS));
        this.newBudgetIncomeSavingsTableView.getItems().removeAll(newBudgetIncomeSavingsObservableList);
        this.newBudgetIncomeSavingsObservableList = incomeSavingsList;
        this.newBudgetIncomeSavingsTableView.getItems().addAll(newBudgetIncomeSavingsObservableList);
    }


    public void saveOrUpdateNewBudgetInDB() {
        if (this.budget == null) {
            this.budget = TransactionInsertionManager.initializeBudget(this.yearMonth);
        }
        for (NewBudgetTableData newBudgetTableData : newBudgetIncomeSavingsObservableList) {
            if (newBudgetTableData.getCategory().equals(Category.INCOME)) {
                this.budget.getBudgetIncome().setIncomeValue(newBudgetTableData.getNewBudgetValue());
            }
            if (newBudgetTableData.getCategory().equals(Category.SAVINGS)) {
                this.budget.getBudgetSavings().setSavingsValue(newBudgetTableData.getNewBudgetValue());
            }
        }
        for (NewBudgetTableData newBudgetTableData : newBudgetExpensesObservableList) {
            System.out.println(newBudgetTableData.getCategory());
            System.out.println(newBudgetTableData.getNewBudgetValue());

            this.budget.getBudgetExpenses().updateCategoryValue(newBudgetTableData.getCategory(), newBudgetTableData.getNewBudgetValue());
        }

        if (budgetDao.updateTransaction(this.budget) != true) {
            budgetDao.addEntity(this.budget);
        }
        ;

    }

    public void setYearMonth() {
        Integer year = Integer.parseInt(this.yearComboBox.getSelectionModel().getSelectedItem());
        Integer month = MonthCode.monthNames().indexOf(this.monthComboBox.getSelectionModel().getSelectedItem()) + 1;
        this.yearMonth = YearMonth.of(year, month);
    }

    public void initializeNewBudgetTableViews() {
        initializeNewBudgetIncomeSavingsObservableList();
        initializeNewBudgetExpensesObservableList();
        setEditableNewBudgetIncomeSavingsTableColumn();
        setEditableNewBudgetExpensesTableColumn();


        this.newBudgetIncomeSavingsTableView.getItems().addAll(newBudgetIncomeSavingsObservableList);
        this.newBudgetExpensesTableView.getItems().addAll(newBudgetExpensesObservableList);
    }

    public void setExpenseTableColumnHeaderAsTotalExpenses() {
        Double total = 0.0;
        for (NewBudgetTableData newBudgetTableData : this.newBudgetExpensesObservableList) {
            total += newBudgetTableData.getNewBudgetValue();
        }
        this.newBudgetExpensesTableColumn.setText(total.toString());
    }


}
