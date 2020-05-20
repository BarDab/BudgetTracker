package com.bardab.budgettracker.model.categories;

import com.bardab.budgettracker.model.Budget;

import javax.persistence.*;

@Entity
@Table (name = "PlannedExpenses",uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class PlannedExpenses extends Categories {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_ID")
    private Budget budget;

    @Column (name = "monthCode")
    private Integer monthCode;
    @Column(name="electricityForecast")
    private Double electricityForecast;
    @Column(name="gasForecast")
    private Double gasForecast;
    @Column(name="rentForecast")
    private Double rentForecast;
    @Column(name="healthCareForecast")
    private Double healthCareForecast;
    @Column(name="entertainmentForecast")
    private Double entertainmentForecast;
    @Column(name="hobbyForecast")
    private Double hobbyForecast;
    @Column(name="transportForecast")
    private Double transportForecast;
    @Column(name="mobileNetworkForecast")
    private Double mobileNetworkForecast;
    @Column(name="internetForecast")
    private Double internetForecast;

    @Column(name="otherSpendingForecast")
    private Double plannedVariable;


    public Budget getBudget() {
        return budget;
    }


    @Override
    public Double getTotalValue() {
        initializeFields(this);
        final Double[] total = {0.0};
        getMapOfCategories(this).forEach((e,f)-> total[0] +=f);

        return total[0];
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public void setMonthCode(Integer monthCode) {
        this.monthCode = monthCode;
    }

    


}
