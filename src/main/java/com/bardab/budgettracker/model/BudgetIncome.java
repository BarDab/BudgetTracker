package com.bardab.budgettracker.model;

import com.bardab.budgettracker.model.additional.Category;
import com.bardab.budgettracker.model.additional.CategoryValueSetter;
import com.vladmihalcea.hibernate.type.basic.YearMonthDateType;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Entity
@Table(name = "plannedIncome")
@TypeDef(
        typeClass = YearMonthDateType.class,
        defaultForType = YearMonth.class
)
public class BudgetIncome implements Serializable, CategoryValueSetter {



    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category = Category.INCOME;

    @OneToOne(fetch = FetchType.LAZY)
    private Budget budget;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;


    @Column(
            name = "yearMonth",
            columnDefinition = "date"
    )
    private YearMonth yearMonth;

    @Column (name = "incomeValue")
    private Double incomeValue;




    @Override
    public void updateCategoryValue(Category category, Double value) {
        if(category.equals(this.category)){
            this.incomeValue+=value;
        }
    }

    @Override
    public Double getCategoryValue(Category category) {
        return this.incomeValue;
    }

    @Override
    public HashMap<Category, Double> getMapOfCategoriesWithValues() {
        HashMap<Category, Double> mapOfCategoriesWithValues = new LinkedHashMap();
        mapOfCategoriesWithValues.put(category,getCategoryValue(category));

        return mapOfCategoriesWithValues;
    }

    @Override
    public void initializeCategoryValues() {
        this.incomeValue = 0.0;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Double getIncomeValue() {
        return incomeValue;
    }

    public void setIncomeValue(Double incomeValue) {
        this.incomeValue = incomeValue;
    }
}

