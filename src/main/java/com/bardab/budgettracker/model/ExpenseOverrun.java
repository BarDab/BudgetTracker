package com.bardab.budgettracker.model;


import com.bardab.budgettracker.model.additional.Category;
import com.bardab.budgettracker.model.additional.CategoryValueSetter;
import com.vladmihalcea.hibernate.type.basic.YearMonthDateType;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Entity
@Table(name = "expenseOverrun")
@TypeDef(
        typeClass = YearMonthDateType.class,
        defaultForType = YearMonth.class
)
public class ExpenseOverrun implements CategoryValueSetter {

    @Id
    private Long id;



    @Column(
            unique = true,
            name = "yearMonth",
            columnDefinition = "date"
    )
    private YearMonth yearMonth;

    @Column
    private Double totalExpenses;


    @Column(name = "food")
    private Double food;
    @Column(name = "household")
    private Double houseHold;
    @Column(name = "clothing")
    private Double clothing;
    @Column(name = "healthCare")
    private Double healthCare;
    @Column(name = "bills")
    private Double bills;
    @Column(name = "transport")
    private Double transport;
    @Column(name = "hobby")
    private Double hobby;
    @Column(name = "dining")
    private Double dining;
    @Column(name = "debt")
    private Double debt;
    @Column(name = "entertainment")
    private Double entertainment;
    @Column(name = "other")
    private Double other;

    private Budget budget;

    private Actual actual;


    @Override
    public void initializeCategoryValues() {
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getType().equals(Double.class)) {
                    if (field.get(this) == null) {
                        field.set(this, 0.0);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.getMessage();
        }
    }

    @Override
    public void updateCategoryValue(Category category, Double value) {

    }

    private void updateCategoryOverrunValue(Category category, Double value) {
        switch (category) {
            case DEBT:
                this.debt += value;
                break;
            case FOOD:
                this.food += value;
                break;
            case CLOTHING:
                this.clothing += value;
                break;
            case HOBBY:
                this.hobby += value;
                break;
            case DINING:
                this.dining += value;
                break;
            case BILLS:
                this.bills += value;
                break;
            case OTHER:
                this.other += value;
                break;
            case HEALTHCARE:
                this.healthCare += value;
                break;
            case TRANSPORT:
                this.transport += value;
                break;
            case ENTERTAINMENT:
                this.entertainment += value;
                break;
        }
    }

    @Override
    public Double getCategoryValue(Category category) {
        switch (category) {
            case DEBT:
                return this.debt;


            case ENTERTAINMENT:
                return this.entertainment;

            case FOOD:
                return this.food;

            case CLOTHING:
                return this.clothing;

            case HOBBY:
                return this.hobby;

            case DINING:
                return this.dining;

            case BILLS:
                return this.bills;

            case HEALTHCARE:
                return this.healthCare;

            case TRANSPORT:
                return this.transport;

        }
        return this.other;
    }

    @Override
    public HashMap<Category, Double> getMapOfCategoriesWithValues() {
        HashMap<Category, Double> mapOfCategoriesWithValues = new LinkedHashMap();
        for (Category category : Category.expenses()) {
            mapOfCategoriesWithValues.put(category, getCategoryValue(category));
        }

        return mapOfCategoriesWithValues;
    }

    public void setOverrunValues() {
        for (Category category : getMapOfCategoriesWithValues().keySet()) {
            updateCategoryOverrunValue(category,
                    budget.getBudgetExpenses().getCategoryValue(category) - actual.getActualExpenses().getCategoryValue(category));
        }
        this.totalExpenses = this.budget.getTotalExpenses() - this.actual.getTotalExpenses();
    }


    public void setActualAndBudgetAndYearMonth(Actual actual, Budget budget, YearMonth yearMonth) {
        setActual(actual);
        setBudget(budget);
        setYearMonth(yearMonth);
    }


    public Double getTotalExpenses() {
        if(totalExpenses==null){
            totalExpenses=0.0;
        }
        return totalExpenses;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Actual getActual() {
        return actual;
    }

    public void setActual(Actual actual) {
        this.actual = actual;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }


}
