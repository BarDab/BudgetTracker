package com.bardab.budgettracker.model;

import com.bardab.budgettracker.model.additional.MonthCode;
import com.bardab.budgettracker.model.categories.FixedCostsForecast;

import javax.persistence.*;


@Entity
@Table (name="BudgetForecast",uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class BudgetForecast {
    public BudgetForecast() {
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID", nullable = false,unique = true,length = 11)
    private Long id;

    @OneToOne (mappedBy = "budgetForecast", cascade = CascadeType.ALL)
    private FixedCostsForecast fixedCostsForecast;

    @OneToOne  (mappedBy = "budgetForecast", cascade = CascadeType.ALL)
    private MonthlyBalance monthlyBalance;




    @Column(name="income")
    private Double income;
    @Column(name="additional_spending")
    private Double additionalSpending;
    @Column(name="saving_goal")
    private Double savingGoal;
    @Column(name="monthCode")
    private String monthCode;
    @Column(name="daily_average_income")
    private Double dailyAverageIncome;
    @Column(name="left_for_daily_spending")
    private Double incomeLeftForDailySpending;
    @Column (name = "total_fixed_spending")
    private Double totalFixedCosts;

    public Double getTotalFixedCosts() {
        return totalFixedCosts;
    }

    public void setTotalFixedCosts() {
        this.totalFixedCosts = fixedCostsForecast.getSumOfFixedCosts();
    }

    public Long getId() {
        return id;
    }

    public Double getDailyAverageIncome() {
        return dailyAverageIncome;
    }

    public void setDailyAverageIncome() {
        this.dailyAverageIncome = income/getNumberOfDaysInMonth();
    }

    public Double getAverageDailyFundsAfterDeductionOfAllCosts() {
        return incomeLeftForDailySpending;
    }

    public void setAverageDailyFundsAfterDeductionOfAllCosts() {
        this.incomeLeftForDailySpending = (income - totalFixedCosts - additionalSpending - savingGoal)/getNumberOfDaysInMonth();
    }

    // this method doesn't differentiate months from leap year
    public int getNumberOfDaysInMonth(){
       return MonthCode.getNumberOfDaysInMonth(this.monthCode);
    }


    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        if(income>=0){
            this.income = income;
        }
        else this.income = 0.0;
    }


    public FixedCostsForecast getFixedCostsForecast() {
        return fixedCostsForecast;
    }

    public void setFixedCostsForecast(FixedCostsForecast fixedCostsForecast) {
        if(monthCode!=null){
        fixedCostsForecast.setMonthCode(getMonthCode());
        }
        this.fixedCostsForecast = fixedCostsForecast;
    }

    public Double getAdditionalSpending() {
        return additionalSpending;
    }

    public void setAdditionalSpending(Double additionalSpending) {
        if(additionalSpending>=0){
            this.additionalSpending = additionalSpending;
        }
        else this.additionalSpending = 0.0;
    }

    public Double getSavingGoal() {
        return savingGoal;
    }

    public void setSavingGoal(Double savingGoal) {
        if(savingGoal>=0){
            this.savingGoal = savingGoal;
        }
        else this.savingGoal = 0.0;
    }

    public String getMonthCode() {
        return monthCode;
    }

    public boolean validateMonthCode(String yearMonth){
     return MonthCode.validateMonthCode(yearMonth);
    }



    public void setMonthCode(String yearMonth) {
        if(validateMonthCode(yearMonth)){
            this.monthCode = yearMonth;
            if(fixedCostsForecast !=null){
                fixedCostsForecast.setMonthCode(yearMonth);
            }
        }
    }
}