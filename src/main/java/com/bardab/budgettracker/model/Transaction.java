package com.bardab.budgettracker.model;
import com.bardab.budgettracker.model.additional.MonthCode;

import javax.persistence.*;
import java.time.LocalDate;



@Entity
@Table (name="Transaction",uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class Transaction {


    public Transaction() {
    }

    @Id
    @GeneratedValue
    private Long id;



    @Column (name="transaction_date" ,nullable = false)
    private LocalDate transactionDate;
    @Column (name = "month_code" ,nullable = false)
    private String monthCode;
    @Column (name="TYPE")
    private String type;
    @Column (name="VALUE")
    private Double value;
    @Column (name="DESCRIPTION")
    private String description;



    //lazy fetching so transactions are loaded on demand, not every time
    @ManyToOne (fetch = FetchType.LAZY)
    private MonthlyBalance monthlyBalance;


    // The child entity, Transaction, implement the equals and hashCode methods.
    // Since we cannot rely on a natural identifier for equality checks, we need
    // to use the entity identifier instead for the equals method. However, you
    // need to do it properly so that equality is consistent across all entity
    // state transitions, which is also the reason why the hashCode has to be
    // a constant value. Because we rely on equality for the removeComment, it’s
    // good practice to override equals and hashCode for the child entity in a bidirectional association.


    public MonthlyBalance getMonthlyBalance() {
        return monthlyBalance;
    }

    public void setMonthlyBalance(MonthlyBalance monthlyBalance) {
        this.monthlyBalance = monthlyBalance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj instanceof Transaction) {
            Transaction other = (Transaction) obj;
            return this.toStringWithoutID().equals(other.toStringWithoutID());
        }
        return false;
    }
    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", transactionDate=" + transactionDate +
                ", value=" + value +
                ", description='" + description + '\'' +
                '}';
    }
    public String toStringWithoutID(){
        return "Transaction{" +
                " type='" + type + '\'' +
                ", transactionDate=" + transactionDate +
                ", value=" + value +
                ", description='" + description + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDateAndMonthCode(LocalDate transactionDate) {
        this.transactionDate=transactionDate;
        this.monthCode = MonthCode.createMonthCodeFromLocalDate(transactionDate);
    }

    public String getMonthCode() {
        return monthCode;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        if(value>=0){
        this.value = value;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}
