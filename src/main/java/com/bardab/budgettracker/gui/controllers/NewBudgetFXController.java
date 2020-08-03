package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.dao.BudgetDao;
import com.bardab.budgettracker.dao.TransactionInsertionManager;
import com.bardab.budgettracker.gui.additional.DoubleFormatter;
import com.bardab.budgettracker.gui.additional.MonthCode;
import com.bardab.budgettracker.model.Budget;
import com.bardab.budgettracker.model.additional.Category;
import com.bardab.budgettracker.model.additional.CategoryFormatter;
import com.bardab.budgettracker.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class NewBudgetFXController {

    private BudgetDao budgetDao = new BudgetDao(HibernateUtil.getInstance().getSessionFactory());


    @FXML
    private DialogPane newBudgetPane;

    @FXML
    private GridPane gridPane;


    private Pane mainWindow;


    @FXML
    private ObservableList<String> yearList = FXCollections.observableArrayList(MonthCode.yearList());
    @FXML
    private ObservableList<String> monthList = FXCollections.observableArrayList(MonthCode.monthNames());


    private ComboBox<String> yearComboBox = new ComboBox<>();

    private ComboBox<String> monthComboBox = new ComboBox<>();

    LinkedHashMap<Label, TextField> categoriesWithValues = new LinkedHashMap<>();


    private Budget budget;

    private YearMonth yearMonth;



    public void init(Pane pane,Budget budget) {
        this.mainWindow = pane;
        this.budget = budget;
//        if(budget==null){
//            this.budget = TransactionInsertionManager.initializeBudget(YearMonth.now());
//        }
//        createGridPaneWithLabelsAndTextFields(this.gridPane);

    }


    public Integer getNumberOfFieldsFromBudget(){
        return Category.values().length;
    }


    public void createGridPaneWithLabelsAndTextFields() {
        LinkedHashMap<Label, TextField> hashMap = new LinkedHashMap<>();
        int column = 0;
        int row = 0;

        initializeYearComboBox();
        gridPane.add(yearComboBox,column,row);
        initializeMonthComboBox();
        gridPane.add(monthComboBox,column+1,row);

        row++;


        for (Category category : Category.allCategoriesInPresentableOrder()) {
            String categoryInPresentableForm = CategoryFormatter.getCategoryNameInPresentable(category);

            Label label = new Label(categoryInPresentableForm);
            TextField textField = new TextField();
            textField.setTextFormatter(new DoubleFormatter().doubleFormatter());
            textField.setText("0.0");


            gridPane.add(label, column, row);
            gridPane.add(textField,column+1,row);


            hashMap.put(label,textField);
            row++;
        }
        this.categoriesWithValues = hashMap;
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
            System.out.println(label.getText()+": "+hashMap.get(label));
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


    public Budget convertInputFromFieldsToBudget(){
        return createNewBudget(getDataFromTextFields(categoriesWithValues),getYearMonth());
    }

    public void addNewBudget() {
        budgetDao.addEntity(convertInputFromFieldsToBudget());
    }

    public boolean updateExistingBudget() {
        if (budgetDao.updateTransaction(convertInputFromFieldsToBudget()) == true) {
        return true;
        }
        else return false;
    }

    public void addOrUpdate(){
        if(!updateExistingBudget()){
            addNewBudget();
        }

    }



    public YearMonth getYearMonth() {
        Integer year = Integer.parseInt(this.yearComboBox.getSelectionModel().getSelectedItem());
        Integer month = MonthCode.monthNames().indexOf(this.monthComboBox.getSelectionModel().getSelectedItem()) + 1;

        return YearMonth.of(year, month);

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



}
