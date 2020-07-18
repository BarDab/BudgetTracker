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
@Table(name = "plannedSavings")
@TypeDef(
        typeClass = YearMonthDateType.class,
        defaultForType = YearMonth.class
)
public class BudgetSavings implements Serializable, CategoryValueSetter {



    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category = Category.SAVINGS;


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

    @Column (name = "savingsValue")
    private Double savingsValue;




    @Override
    public void updateCategoryValue(Category category, Double value) {
        if(category.equals(this.category)){
            this.savingsValue+=value;
        }
    }

    @Override
    public Double getCategoryValue(Category category) {
        return this.savingsValue;
    }

    @Override
    public HashMap<Category, Double> getMapOfCategoriesWithValues() {
        HashMap<Category, Double> mapOfCategoriesWithValues = new LinkedHashMap();
        mapOfCategoriesWithValues.put(category,getCategoryValue(category));

        return mapOfCategoriesWithValues;
    }

    @Override
    public void initializeCategoryValues() {
        this.savingsValue = 0.0;
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

    public Double getSavingsValue() {
        return savingsValue;
    }

    public void setSavingsValue(Double savingsValue) {
        this.savingsValue = savingsValue;
    }
}
