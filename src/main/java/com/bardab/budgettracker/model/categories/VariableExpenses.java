package com.bardab.budgettracker.model.categories;


import com.bardab.budgettracker.model.MonthlyBalance;
import com.bardab.budgettracker.model.Transaction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "VariableExpenses")
public class VariableExpenses extends Categories implements Serializable {

    public VariableExpenses() {
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthlyBalance_ID")
    private MonthlyBalance monthlyBalance;


    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer monthCode;

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
    @Column(name = "events")
    private Double events;
    @Column(name = "gifts")
    private Double gifts;
    @Column(name = "investments")
    private Double investments;
    @Column(name = "travel")
    private Double travel;
    @Column(name = "fees")
    private Double fees;
    @Column(name = "development")
    private Double development;
    @Column(name = "books")
    private Double books;
    @Column(name = "other")
    private Double other;


    @Override
    public Double getTotalValue() {
        initializeFields(this);
        final Double[] total = {0.0};
        getMapOfCategories(this).forEach((e,f)-> total[0] +=f);

        return total[0];
    }


    public void addTransactionValue(Transaction transaction) {
        this.monthCode = transaction.getMonthCode();
        updateValue(this,transaction.getCategory(),transaction.getValue());
    }



    public void setMonthlyBalance(MonthlyBalance monthlyBalance) {
        this.monthlyBalance = monthlyBalance;
    }

    public Integer getMonthCode() {
        return monthCode;
    }

    public void setMonthCode(Integer monthCode) {
        this.monthCode = monthCode;
    }

    public Double getFood() {
        return food;
    }




}
