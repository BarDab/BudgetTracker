package com.bardab.budgettracker.model;


import com.bardab.budgettracker.model.categories.FixedCosts;
import com.bardab.budgettracker.model.categories.VariableCosts;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MonthlyBalance")
public class MonthlyBalance implements Serializable {

    public MonthlyBalance() {
    }


    // how those generation types work?
    @Id
    @GeneratedValue
    private Long id;


    @NaturalId
    @Column( unique = true)
    private Integer monthCode;


    @Column(name = "total_spending")
    private Double totalSpending;

    @Column(name = "total_income")
    private Double totalIncome;

    @Column(name = "daily_balance")
    private Double balance;

    @Column(name = "average_daily_spending")
    private Double averageSpending;

    @Column(name = "average_spending_excluding_fixed_costs")
    private Double averageSpendingExcludingFixedCosts;

    @Column(name = "number_of_transactions")
    private Integer numberOfTransactions;


    @OneToOne(mappedBy = "monthlyBalance",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private FixedCosts fixedCosts;

    @OneToOne(mappedBy = "monthlyBalance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private VariableCosts variableCosts;


//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
//    private BudgetForecast budgetForecast;


//    @OneToMany ( mappedBy = "monthlyBalance",cascade = CascadeType.ALL, orphanRemoval = true )
//    private List<Transaction> transactions = new ArrayList<>();

    public void removeTransaction(Transaction transaction) {
//        this.transactions.remove(transaction);
//        transaction.setMonthlyBalance(null);
    }

    public VariableCosts getVariableCosts() {
        return variableCosts;
    }

    public void setVariableCosts(VariableCosts variableCosts) {
        this.variableCosts = variableCosts;
        variableCosts.setMonthlyBalance(this); }

    public FixedCosts getFixedCosts() {
        return fixedCosts;
    }

    public void setFixedCosts(FixedCosts fixedCosts) {
        this.fixedCosts = fixedCosts;
        fixedCosts.setMonthlyBalance(this);
    }



    public void updateAllFields(Transaction transaction) {
        initializeValueFieldsAndNumberOfTransactions();
        updateValueFieldsAndNumberOfTransactions(transaction);
        setMonthCode(transaction.getMonthCode());
        variableCosts.updateWithTransaction(transaction);
        fixedCosts.updateWithTransaction(transaction);
    }

    public void setMonthCode(Integer monthCode) {
        this.monthCode = monthCode;
    }

    public void initializeValueFieldsAndNumberOfTransactions() {
        if (this.totalSpending == null) {
            this.totalSpending = 0.0;
        }
        if (this.totalIncome == null) {
            this.totalIncome = 0.0;
        }
        if (this.balance == null) {
            this.balance = 0.0;
        }
        if (this.numberOfTransactions == null) {
            this.numberOfTransactions = 0;
        }
    }

    // be careful with those values, not thought through all possible outcomes and relations with other classes
    public void updateValueFieldsAndNumberOfTransactions(Transaction transaction) {
        if (transaction != null) {
            if (transaction.getType().equalsIgnoreCase("income")) {
                this.totalIncome += transaction.getValue();
                this.balance += transaction.getValue();
            } else {
                this.totalSpending += transaction.getValue();
                this.balance -= transaction.getValue();
                System.out.println(balance);
            }
            this.numberOfTransactions++;
        }
    }


    public Double getTotalSpending() {
        return totalSpending;
    }

    @Override
    public String toString() {
        return "MonthlyBalance{" +
                "fixedCosts=" + fixedCosts +
                ", variableCosts=" + variableCosts +
                '}';
    }

    //    public void setTotalSpending() {
//        if(transactions !=null){
//        for(Transaction transaction: transactions){
//            this.totalSpending += transaction.getValue();
//        }
//        }
//        else this.totalSpending=0.0;
//    }
}
