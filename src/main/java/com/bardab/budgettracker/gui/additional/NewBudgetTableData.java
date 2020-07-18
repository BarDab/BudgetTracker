package com.bardab.budgettracker.gui.additional;

import com.bardab.budgettracker.dao.BudgetDao;
import com.bardab.budgettracker.model.Budget;
import com.bardab.budgettracker.model.additional.Category;
import com.bardab.budgettracker.util.HibernateUtil;
import javafx.beans.property.SimpleDoubleProperty;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class NewBudgetTableData {



    private SimpleDoubleProperty newBudgetValue;
    private Category category;

    public NewBudgetTableData(Double newBudgetValue, Category category) {
        this.newBudgetValue = new SimpleDoubleProperty(newBudgetValue);
        this.category = category;
    }

    public double getNewBudgetValue() {
        return newBudgetValue.get();
    }

    public void setNewBudgetValue(double newBudgetValue) {
        this.newBudgetValue.set(newBudgetValue);
    }

    public static List<NewBudgetTableData> budgetActualOverrunTableDataList (YearMonth yearMonth){
        List<NewBudgetTableData> list = new ArrayList<>();


        Budget budget = getBudgetFromDB(yearMonth);

        for(Category category:Category.values()){
            Double budgetValue = budget.getBudgetExpenses().getCategoryValue(category);

            list.add(new NewBudgetTableData(budgetValue,category));
        }

        return list;
    }

    private static Budget getBudgetFromDB(YearMonth yearMonth) {
        BudgetDao budgetDao = new BudgetDao(HibernateUtil.getInstance().getSessionFactory());
        try {
            return budgetDao.findByYearMonth(yearMonth);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Budget();
    }

    public Category getCategory() {
        return category;
    }
}
