package com.bardab.budgettracker.model.categories;

import com.bardab.budgettracker.model.MonthlyBalance;
import com.bardab.budgettracker.model.Transaction;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FixedExpenses")
public class FixedExpenses extends Categories implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthlyBalance_ID")
    private MonthlyBalance monthlyBalance;

    @Column
    private Integer monthCode;

    @Column(name = "electricity_fixed")
    private Double electricityFixed;
    @Column(name = "gas_fixed")
    private Double gasFixed;
    @Column(name = "rent_fixed")
    private Double rentFixed;
    @Column(name = "other_fixed")
    private Double otherFixed;
    @Column(name = "healthCare_fixed")
    private Double healthCareFixed;
    @Column(name = "entertainment_fixed")
    private Double entertainmentFixed;
    @Column(name = "hobby_fixed")
    private Double hobbyFixed;
    @Column(name = "transport_fixed")
    private Double transportFixed;
    @Column(name = "mobileNetwork_fixed")
    private Double mobileNetworkFixed;
    @Column(name = "internet_fixed")
    private Double internetFixed;


    @Override
    public Double getTotalValue() {
        initializeFields(this);
        final Double[] total = {0.0};
        getMapOfCategories(this).forEach((e,f)-> total[0] +=f);

        return total[0];
    }

    public void addTransactionValue(Transaction transaction) {
        this.monthCode = transaction.getMonthCode();
        updateValue(this,transaction);
    }


    public Integer getMonthCode() {
        return monthCode;
    }

    public void setMonthCode(Integer monthCode) {
        this.monthCode = monthCode;
    }

    public void setMonthlyBalance(MonthlyBalance monthlyBalance) {
        this.monthlyBalance = monthlyBalance;
    }

}
