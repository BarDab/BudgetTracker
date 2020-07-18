package com.bardab.budgettracker.model;

import com.bardab.budgettracker.model.additional.Category;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Entity
@Table (name = "spendingManager")
public class SpendingManager {

    @Id
    @GeneratedValue
    private Long id;


    @NaturalId
    @Column(
            unique = true,
            name = "yearMonth",
            columnDefinition = "date"
    )
    private YearMonth yearMonth;

    @Column (name = "totalCashAtHand")
    private Double cashAtHand;

    @Column (name = "totalSavings")
    private Double savings;

    @Column (name = "alreadySpent")
    private Double spent;

    @Column (name="daysToNextPayment")
    private Integer daysToNextPayment;



    public HashMap<String,Double> getMapOfCategoriesWithValues(){
        HashMap<String, Double> hashMap = new LinkedHashMap<>();
        hashMap.put("Cash at Hand", cashAtHand);
        hashMap.put("Savings", savings);
        hashMap.put("Spent", spent);
        hashMap.put("Days until payment",daysToNextPayment.doubleValue());
        hashMap.put("Average daily CaH",getAverageDailyCashAtHand());

        return hashMap;
    }

    public void initializeFields(){
        if(this.yearMonth==null){ this.yearMonth = YearMonth.now();}
        if(this.daysToNextPayment==null){ this.daysToNextPayment=0;}
        if(this.cashAtHand==null){this.cashAtHand=0.0;}
        if(this.savings==null){this.savings=0.0;}
        if(this.spent==null){this.spent=0.0;}
    }


    public void updateWithTransactions(Transaction transaction){

        if(transaction.getCategory().equals(Category.INCOME)){
            if(this.cashAtHand==null){this.cashAtHand=0.0;}
            cashAtHand+=transaction.getValue();
        }
        else if(transaction.getCategory().equals(Category.SAVINGS)){
            if(this.savings==null){this.savings=0.0;}
            savings+=transaction.getValue();
            cashAtHand-=transaction.getValue();
        }
        else {
            if(this.spent==null){this.spent=0.0;}
            spent+=transaction.getValue();
            cashAtHand-=transaction.getValue();
        }
    }


    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public Double getCashAtHand() {
        return cashAtHand;
    }

    public Double getSavings() {
        return savings;
    }

    public Double getSpent() {
        return spent;
    }

    public Integer getDaysToNextPayment() {
        return daysToNextPayment;
    }

    public Double getAverageDailyCashAtHand(){
        return this.cashAtHand /this.daysToNextPayment;

    }


    public void setDaysToNextPayment(Integer dayOfPayment) {
        try{
            this.daysToNextPayment = LocalDate.now().datesUntil(getDateOfNextPayment(dayOfPayment)).toArray().length;
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    private LocalDate getDateOfNextPayment(Integer dayOfPayment) {
        LocalDate dateOfNextPayment=LocalDate.of(1900,1,1);
        if(dayOfPayment<=31&dayOfPayment>=1){

        if (LocalDate.now().getDayOfMonth() >= dayOfPayment) {
            if (LocalDate.now().getMonthValue() != 12) {
                dateOfNextPayment = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue() + 1, dayOfPayment);
            } else dateOfNextPayment = LocalDate.of(LocalDate.now().getYear() + 1, 1, dayOfPayment);
        } else
            dateOfNextPayment = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), dayOfPayment);
        }
        return dateOfNextPayment;
    }
}
