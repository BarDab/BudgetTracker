package com.bardab.budgettracker.model;

import com.bardab.budgettracker.model.additional.MonthCode;
import com.bardab.budgettracker.model.categories.Categories;
import com.bardab.budgettracker.model.categories.FixedExpenses;
import com.bardab.budgettracker.model.categories.VariableExpenses;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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

    @Column(nullable = false)
    private Integer monthCode;
    @Column(name = "category")
    private String category;
    @Column(name = "value")
    private Double value;
    @Column(name = "description")
    private String description;


    public void setCategoryAndTransformToCamelCase(String category) {
        category = Categories.transformToCamelCase(category);
        if(Categories.validateType(category, getAllCategories())){
            this.category = category;
        }
    }
    public void setPresentableCategory(String category){
        this.category = category;
    }
    public List<String> getAllCategories(){
        FixedExpenses fixedExpenses = new FixedExpenses();
        VariableExpenses variableExpenses = new VariableExpenses();
        List<String> allCategories = new ArrayList<>();

        allCategories.addAll(fixedExpenses.getCategoriesNames(fixedExpenses));
        allCategories.addAll(variableExpenses.getCategoriesNames(variableExpenses));

        return allCategories;
    }

    public void setTransactionDateAndMonthCode(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
        this.monthCode = MonthCode.createIntMonthCodeFromLocalDate(transactionDate);
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public Integer getMonthCode() {
        return monthCode;
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
