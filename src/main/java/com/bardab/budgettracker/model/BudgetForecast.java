package com.bardab.budgettracker.model;
import com.bardab.budgettracker.dao.FixedCostsDao;

import javax.persistence.*;
import java.time.YearMonth;


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
    private FixedCosts fixedCosts;

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
    private Double totalFixedSpending;

    public Double getTotalFixedSpending() {
        return totalFixedSpending;
    }

    public void setTotalFixedSpending() {
        this.totalFixedSpending = fixedCosts.getSum();
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

    public Double getIncomeLeftForDailySpending() {
        return incomeLeftForDailySpending;
    }

    public void setIncomeLeftForDailySpending() {
        this.incomeLeftForDailySpending = (income - fixedCosts.getSum() - additionalSpending - savingGoal)/getNumberOfDaysInMonth();
    }

    // this method doesn't differentiate moths from leap year
    public int getNumberOfDaysInMonth(){
        YearMonth yearMonth = YearMonth.of(Integer.parseInt(this.monthCode.substring(0,3)),Integer.parseInt(this.monthCode.substring(5,6)));
        return yearMonth.lengthOfMonth();
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


    public FixedCosts getFixedCosts() {
        return fixedCosts;
    }

    public void setFixedCosts(FixedCosts fixedCosts) {
        if(monthCode!=null){
        fixedCosts.setMonthCode(getMonthCode());
        }
        this.fixedCosts = fixedCosts;
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
            if(fixedCosts!=null){
                fixedCosts.setMonthCode(yearMonth);
            }
        }
    }
}