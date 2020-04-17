package com.bardab.budgettracker.model;
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

    @Column(name="income")
    private Double income;
    @Column(name="fixed_spending")
    private Double fixedSpending;
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
        this.incomeLeftForDailySpending = (income - fixedSpending - additionalSpending - savingGoal)/getNumberOfDaysInMonth();
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

    public Double getFixedSpending() {
        return fixedSpending;
    }

    public void setFixedSpending(Double fixedSpending) {
        if(fixedSpending>=0){
            this.fixedSpending = fixedSpending;
        }
        else this.fixedSpending = 0.0;
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
        }
    }
}