package com.bardab.budgettracker.model;

import com.bardab.budgettracker.model.additional.Category;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.YearMonth;


@Entity
@Table(name = "Transaction", uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class Transaction  {


    public Transaction() {
    }

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;


    @Column(name = "transactionDate", nullable = false)
    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;
    @Column(name = "value")
    private Double value;
    @Column(name = "description")
    private String description;


    public void setCategory(Category category) {
        this.category = category;
    }


    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;

    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }


    public YearMonth getYearMonth() {
        return  YearMonth.from(transactionDate);
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        if (value >= 0) {
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
