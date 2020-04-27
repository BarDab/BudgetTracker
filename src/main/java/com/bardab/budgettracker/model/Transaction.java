package com.bardab.budgettracker.model;

import com.bardab.budgettracker.model.additional.MonthCode;
import com.bardab.budgettracker.model.additional.VariableTypesConverter;
import com.bardab.budgettracker.model.categories.FixedCosts;
import com.bardab.budgettracker.model.categories.VariableCosts;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;


@Entity
@Table(name = "Transaction", uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@NaturalIdCache
public class Transaction {


    public Transaction() {
    }

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;


    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(nullable = false)
    private Integer monthCode;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "VALUE")
    private Double value;
    @Column(name = "DESCRIPTION")
    private String description;



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
        ArrayList<String> arrayListFixed = VariableTypesConverter.toStringToLinkedListConvertingOnlyNames(FixedCosts.fixedCostsTypes());
        if (type.matches("(.+)fixed")) {
            for (String t : arrayListFixed) {
                if (type.equalsIgnoreCase(t)) {
                    this.type = type;
                    return;
                }
            }
        }
        ArrayList<String> arrayListVariable = VariableTypesConverter.toStringToLinkedListConvertingOnlyNames(VariableCosts.variableCostsTypes());
        for (String t : arrayListVariable) {
            System.out.println(t);
            if (type.equalsIgnoreCase(t)) {
                this.type = type;
                return;
            }
        }

        System.out.println("No such type");
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDateAndMonthCode(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
        this.monthCode = MonthCode.createIntMonthCodeFromLocalDate(transactionDate);
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
