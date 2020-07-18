package com.bardab.budgettracker.model;

import com.bardab.budgettracker.model.additional.Category;
import com.vladmihalcea.hibernate.type.basic.YearMonthDateType;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.time.YearMonth;


@Entity
@Table (name="Budget")
@TypeDef(
        typeClass = YearMonthDateType.class,
        defaultForType = YearMonth.class
)
public class Budget implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @Column(
            unique = true,
            name = "yearMonth",
            columnDefinition = "date"
    )
    private YearMonth yearMonth;

    @OneToOne (mappedBy = "budget", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BudgetExpenses budgetExpenses;

    @OneToOne(mappedBy = "budget", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private BudgetSavings budgetSavings;

    @OneToOne(mappedBy = "budget", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private BudgetIncome budgetIncome;




    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public BudgetExpenses getBudgetExpenses() {
        return budgetExpenses;
    }

    public void setBudgetExpenses(BudgetExpenses budgetExpenses) {
        this.budgetExpenses = budgetExpenses;
        this.budgetExpenses.setBudget(this);
        this.budgetExpenses.setYearMonth(this.yearMonth);
    }

    public BudgetSavings getBudgetSavings() {
        return budgetSavings;
    }

    public void setBudgetSavings(BudgetSavings budgetSavings) {
        this.budgetSavings = budgetSavings;
        this.budgetSavings.setBudget(this);
        this.budgetSavings.setYearMonth(this.yearMonth);
    }

    public BudgetIncome getBudgetIncome() {
        return budgetIncome;
    }

    public void setBudgetIncome(BudgetIncome budgetIncome) {
        this.budgetIncome = budgetIncome;
        this.budgetIncome.setBudget(this);
        this.budgetIncome.setYearMonth(this.yearMonth);
    }



    public Double getTotalExpenses(){
        Double totalExpenses=0.0;
        for(Category category: this.budgetExpenses.getMapOfCategoriesWithValues().keySet()){
            totalExpenses+=this.budgetExpenses.getMapOfCategoriesWithValues().get(category);
        }
        return totalExpenses;
    }


}