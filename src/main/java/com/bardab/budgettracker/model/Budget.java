package com.bardab.budgettracker.model;

import com.bardab.budgettracker.model.categories.PlannedExpenses;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.YearMonth;


@Entity
@Table (name="Budget")
public class Budget implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @Column( unique = true)
    private Integer monthCode;

    @OneToOne (mappedBy = "budget", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PlannedExpenses plannedExpenses;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "income")
    private Double income;

    @Column(name = "totalSpending")
    private Double totalSpending;

    @Column (name = "totalFixedExpenses")
    private Double totalFixedExpenses;

    @Column (name = "totalVariableExpenses")
    private Double totalVariableExpenses;

    @Column(name="investments")
    private Double investments;

    public void setIncome(Double income) {
        if(income>=0){
            this.income = income;
        }
        else this.income = 0.0;
    }

    public void setPlannedExpenses(PlannedExpenses plannedExpenses) {
        if(monthCode!=null){
        plannedExpenses.setMonthCode(getMonthCode());
        }
        this.plannedExpenses = plannedExpenses;
    }


    public void setTotalVariableExpenses(Double totalVariableExpenses) {
        if(totalVariableExpenses >=0){
            this.totalVariableExpenses = totalVariableExpenses;
        }
        else this.totalVariableExpenses = 0.0;
    }

    public void setInvestments(Double investments) {
        if(investments >=0){
            this.investments = investments;
        }
        else this.investments = 0.0;
    }

    public void setMonthCode(Integer monthCode) {
        this.monthCode = monthCode;
    }


    public void setTotalSpending() {
        this.totalSpending = plannedExpenses.getTotalValue();
    }


    public void setBalance(){
        this.balance = income - totalSpending - investments;
    }
    public Double getBalance(){
       return this.balance;
    }


    public int getNumberOfDaysInMonth(){
        Integer year = Integer.parseInt( monthCode.toString().substring(0,3));
        Integer month = Integer.parseInt( monthCode.toString().substring(4,5));
        return YearMonth.of(year,month).lengthOfMonth();
    }



    public Double getTotalSpending() {
        return totalSpending;
    }

    public Double getIncome() {
        return income;
    }

    public Integer getMonthCode() {
        return monthCode;
    }


    public Double getInvestments() {
        return investments;
    }

    public Double getTotalVariableExpenses() {
        return totalVariableExpenses;
    }

    public PlannedExpenses getPlannedExpenses() {
        return plannedExpenses;
    }

    public Double getDailyAverageExpenses() {
        return this.totalSpending/getNumberOfDaysInMonth();
    }

    public Double getTotalFixedExpenses() {
        return totalFixedExpenses;
    }

    public Long getId() {
        return id;
    }

    public Double getDailyAverageIncome() {
        return this.income/getNumberOfDaysInMonth();
    }



}