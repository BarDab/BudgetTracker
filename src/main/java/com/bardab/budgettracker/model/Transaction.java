package com.bardab.budgettracker.model;

import org.hibernate.annotations.CollectionType;

import javax.persistence.*;
import java.util.Date;



@Entity
@Table (name="Transaction",uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class Transaction {


    public Transaction() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID", nullable = false,unique = true,length = 11)
    private Long id;

    @Column (name="TYPE", nullable = true)
    private String type;

    @Column (name="transaction_time", nullable = true)
    private Date transactionTime;

    @Column (name="VALUE", nullable = true)
    private Double value;

    @Column (name="DESCRIPTION", nullable = true)
    private String description;


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

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", transactionTime=" + transactionTime +
                ", value=" + value +
                ", description='" + description + '\'' +
                '}';
    }
}
