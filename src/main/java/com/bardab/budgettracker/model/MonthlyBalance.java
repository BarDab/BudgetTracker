package com.bardab.budgettracker.model;


import com.bardab.budgettracker.model.categories.FixedExpenses;
import com.bardab.budgettracker.model.categories.VariableExpenses;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;

@Entity
@Table(name = "MonthlyBalance")
public class MonthlyBalance  implements Serializable {

    public MonthlyBalance() {
    }



    // how those generation types work?
    @Id
    @GeneratedValue
    private Long id;


    @NaturalId
    @Column( unique = true)
    private Integer monthCode;

    @Column
    private Integer daysLeft;

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


    @Column(name = "numberOfTransactions")
    private Integer numberOfTransactions;






    @OneToOne(mappedBy = "monthlyBalance",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private FixedExpenses fixedExpenses;

    @OneToOne(mappedBy = "monthlyBalance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private VariableExpenses variableExpenses;



    public void manageTransactionInsertion(Transaction transaction) {

        initializeFields(transaction);
        updateVariableAndFixedExpenses(transaction);
        updateTotalSpendingIncomeAndBalance(transaction);
//        setAverages();

    }
    public void initializeFields(Transaction transaction) {

        try{
            for(Field field: this.getClass().getDeclaredFields()){
                field.setAccessible(true);
                if(field.getType().equals(Double.class)){
                    if(field.get(this)==null){
                        field.set(this,0.0);
                    }
                }
            }
            setMonthCode(transaction);
            if(this.numberOfTransactions==null){
                numberOfTransactions=0;
            }
        }
        catch (IllegalAccessException e){
            e.getMessage();
        }

    }

    public void updateVariableAndFixedExpenses(Transaction transaction){
        variableExpenses.addTransactionValue(transaction);
        fixedExpenses.addTransactionValue(transaction);
        totalFixedExpenses = fixedExpenses.getTotalValue();
        totalVariableExpenses = variableExpenses.getTotalValue();
    }

    // be careful with those values, not thought through all possible outcomes and relations with other classes
    public void updateTotalSpendingIncomeAndBalance(Transaction transaction) {
        if (transaction != null) {
            if (transaction.getCategory().equalsIgnoreCase("income")) {
                this.income += transaction.getValue();
                this.balance += transaction.getValue();
            } else {
                this.totalSpending += transaction.getValue();
                this.balance -= transaction.getValue();
                System.out.println(balance);
            }
            this.numberOfTransactions++;
        }

    }



//    public void setAverages(){
//        this.dailyAverageTotalSpending = this.totalSpending/LocalDate.now().getDayOfMonth();
//        if(this.variableExpenses.getTotalValue()>0) {
//            this.dailyAverageVariable = this.variableExpenses.getTotalValue()/LocalDate.now().getDayOfMonth();
//        }
//    }





    public void setVariableExpenses(VariableExpenses variableExpenses) {
        this.variableExpenses = variableExpenses;
        variableExpenses.setMonthlyBalance(this); }



    public void setFixedExpenses(FixedExpenses fixedExpenses) {
        this.fixedExpenses = fixedExpenses;
        fixedExpenses.setMonthlyBalance(this);
    }

    public void setMonthCode(Transaction transaction) {
        this.monthCode = transaction.getMonthCode();
    }

    public Double getInvestments() {
        return investments;
    }

    public void setInvestments(Double investments) {
        if(investments>0){
        this.investments += investments;
        }
    }

    public FixedExpenses getFixedExpenses() {
        return fixedExpenses;
    }

    public VariableExpenses getVariableExpenses() {
        return variableExpenses;
    }

    public Double getTotalSpending() {
        return totalSpending;
    }



    public Integer getDaysLeft() {
        return daysLeft;
    }

    public Double getIncome() {
        return income;
    }

    public Double getBalance() {
        return balance;
    }

    public Integer getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public Double getTotalFixedExpenses() {
        return totalFixedExpenses;
    }

    public Double getTotalVariableExpenses() {
        return totalVariableExpenses;
    }


    public Integer getMonthCode() {
        return monthCode;
    }
}
