package com.bardab.budgettracker.model;


import com.bardab.budgettracker.model.categories.FixedCostsReal;
import com.bardab.budgettracker.model.categories.VariableCostsReal;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MonthlyBalance",uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class MonthlyBalance {

    public MonthlyBalance() {
    }


    // how those generation types work?
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID", nullable = false,unique = true,length = 11)
    private Long id;
    @Column (name = "month_code")
    private String monthCode;

    @Column (name = "total_spending")
    private Double totalSpending;

    @Column (name = "total_income")
    private Double totalIncome;

    @Column (name = "daily_balance")
    private Double balance;

    @Column (name = "average_daily_spending")
    private Double averageSpending;

    @Column (name = "average_spending_excluding_fixed_costs")
    private Double averageSpendingExcludingFixedCosts;

    @Column (name = "number_of_transactions")
    private Integer numberOfTransactions;

//
    @OneToOne (mappedBy = "monthlyBalance", cascade = CascadeType.ALL)
    private FixedCostsReal fixedCostsReal;
//
    @OneToOne (mappedBy = "monthlyBalance", cascade = CascadeType.ALL)
    private VariableCostsReal variableCostsReal;


//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
//    private BudgetForecast budgetForecast;


    @OneToMany ( mappedBy = "monthlyBalance",cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Transaction> transactions = new ArrayList<>();

    public void removeTransaction(Transaction transaction){
        this.transactions.remove(transaction);
        transaction.setMonthlyBalance(null);
    }

    public VariableCostsReal getVariableCostsReal() {
        return variableCostsReal;
    }

    public void setVariableCostsReal(VariableCostsReal variableCostsReal) {
        this.variableCostsReal = variableCostsReal;
        variableCostsReal.setMonthlyBalance(this);
    }

    public FixedCostsReal getFixedCostsReal() {
        return fixedCostsReal;
    }

    public void setFixedCostsReal(FixedCostsReal fixedCostsReal) {
        this.fixedCostsReal = fixedCostsReal;
        fixedCostsReal.setMonthlyBalance(this);
    }

    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
        transaction.setMonthlyBalance(this);
        updateAllFields(transaction);
    }

    public void updateAllFields(Transaction transaction){
        initializeValueFieldsAndNumberOfTransactions();
        updateValueFieldsAndNumberOfTransactions(transaction);
        setMonthCode(transaction.getMonthCode());
    }

    public void setMonthCode(String monthCode){
        this.monthCode = monthCode;
    }

    public void initializeValueFieldsAndNumberOfTransactions(){
        if(this.totalSpending==null) {
            this.totalSpending=0.0;
        }
        if(this.totalIncome==null) {
            this.totalIncome=0.0;
        }
        if(this.balance==null) {
            this.balance=0.0;
        }
        if(this.numberOfTransactions==null) {
            this.numberOfTransactions=0;
        }
    }

    // be careful with those values, not thought through all possible outcomes and relations with other classes
    public void updateValueFieldsAndNumberOfTransactions(Transaction transaction) {
        if(transaction!=null){
            if(transaction.getValue()>0){
                this.totalIncome+=transaction.getValue();
                this.balance+=transaction.getValue();
                this.numberOfTransactions++;
            }
            else if (transaction.getValue()<0) {
                this.totalSpending+=transaction.getValue();
                this.balance-=transaction.getValue();
                this.numberOfTransactions++;
            }
        }
    }


    public Double getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending() {
        if(transactions !=null){
        for(Transaction transaction: transactions){
            this.totalSpending += transaction.getValue();
        }
        }
        else this.totalSpending=0.0;
    }
}
