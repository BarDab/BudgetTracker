package com.bardab.budgettracker.model;


import com.bardab.budgettracker.model.additional.Category;
import com.vladmihalcea.hibernate.type.basic.YearMonthDateType;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.time.YearMonth;

@Entity
@Table(name = "Actual")
@TypeDef(
        typeClass = YearMonthDateType.class,
        defaultForType = YearMonth.class
)
public class Actual implements Serializable {

    public Actual() {
    }

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


    @OneToOne(mappedBy = "actual", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private ActualExpenses actualExpenses;

    @OneToOne(mappedBy = "actual", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private ActualSavings actualSavings;

    @OneToOne(mappedBy = "actual", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private ActualIncome actualIncome;



    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public ActualExpenses getActualExpenses() {
        return actualExpenses;
    }

    public void setActualExpenses(ActualExpenses actualExpenses) {
        this.actualExpenses = actualExpenses;
        this.actualExpenses.setActual(this);
        this.actualExpenses.setYearMonth(this.yearMonth);
    }

    public ActualSavings getActualSavings() {
        return actualSavings;
    }

    public void setActualSavings(ActualSavings actualSavings) {
        this.actualSavings = actualSavings;
        this.actualSavings.setActual(this);
        this.actualSavings.setYearMonth(this.yearMonth);
    }

    public ActualIncome getActualIncome() {
        return actualIncome;
    }

    public void setActualIncome(ActualIncome actualIncome) {
        this.actualIncome = actualIncome;
        this.actualIncome.setActual(this);
        this.actualIncome.setYearMonth(this.yearMonth);
    }


    public Double getTotalExpenses(){
        Double totalExpenses=0.0;
        for(Category category: this.actualExpenses.getMapOfCategoriesWithValues().keySet()){
            totalExpenses+=this.actualExpenses.getMapOfCategoriesWithValues().get(category);
        }
        return totalExpenses;
    }

}
