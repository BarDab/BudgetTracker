package com.bardab.budgettracker.model.categories;


import com.bardab.budgettracker.model.MonthlyBalance;
import com.bardab.budgettracker.model.Transaction;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "VariableCosts")
public class VariableCosts implements Serializable {

    public VariableCosts() {
    }

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthlyBalance_ID")
    private MonthlyBalance monthlyBalance;


    @Column
    private Integer monthCode;

    @Column(name = "food")
    private Double food;

    @Column(name = "household")
    private Double houseHold;

    @Column(name = "clothing")
    private Double clothing;

    @Column(name = "health")
    private Double healthCare;

    @Column(name = "bills")
    private Double bills;

    @Column(name = "transport")
    private Double transport;

    @Column(name = "hobby")
    private Double hobby;

    @Column(name = "dining")
    private Double dining;

    @Column(name = "events")
    private Double events;

    @Column(name = "gifts")
    private Double gifts;

    @Column(name = "investments")
    private Double investments;

    @Column(name = "travel")
    private Double travel;

    @Column(name = "fees")
    private Double fees;

    @Column(name = "development")
    private Double development;

    @Column(name = "books")
    private Double books;

    @Column(name = "other")
    private Double other;


    public MonthlyBalance getMonthlyBalance() {
        return monthlyBalance;
    }

    public void setMonthlyBalance(MonthlyBalance monthlyBalance) {
        this.monthlyBalance = monthlyBalance;
    }


    public static List<String> getTypes() {
        List<String> transactionTypes = List.of("Food", "Household","Clothing","Health","Bills","Transport",
                "Hobby","Dining","Events","Gifts","Investments","Travel","Fees","Development","Books", "Other");
        return transactionTypes;
    }

    public void updateWithTransaction(Transaction transaction) {
        this.monthCode = transaction.getMonthCode();

        String transactionType = transaction.getType().toLowerCase();
        Double transactionValue = transaction.getValue();
        switch (transactionType) {
            case "food":
                addValueOrInitializeFoodValue(transactionValue);
                break;
            case "houseHold":
                addValueOrInitializeHouseHoldValue(transactionValue);
                break;
            case "clothing":
                addValueOrInitializeClothingValue(transactionValue);
                break;
            case "health":
                addValueOrInitializeHealthCareValue(transactionValue);
                break;
            case "bills":
                addValueOrInitializeBillsValue(transactionValue);
                break;
            case "transport":
                addValueOrInitializeTransportValue(transactionValue);
                break;
            case "hobby":
                addValueOrInitializeHobbyValue(transactionValue);
                break;
            case "dining":
                addValueOrInitializeDiningValue(transactionValue);
                break;
            case "events":
                addValueOrInitializeEventsValue(transactionValue);
                break;
            case "gifts":
                addValueOrInitializeGiftsValue(transactionValue);
                break;
            case "investments":
                addValueOrInitializeInvestmentsValue(transactionValue);
                break;
            case "travel":
                addValueOrInitializeTravelValue(transactionValue);
                break;
            case "fees":
                addValueOrInitializeFeesValue(transactionValue);
                break;
            case "development":
                addValueOrInitializeDevelopmentValue(transactionValue);
                break;
            case "books":
                addValueOrInitializeBooksValue(transactionValue);
                break;
            case "other":
                addValueOrInitializeOtherValue(transactionValue);
                break;
        }
    }


    public Integer getMonthCode() {
        return monthCode;
    }

    public void setMonthCode(Integer monthCode) {
        this.monthCode = monthCode;
    }


    private void addValueOrInitializeFoodValue(Double newValue) {
        if (this.food == null) {
            this.food = newValue;
        } else food += newValue;
    }

    private void addValueOrInitializeHouseHoldValue(Double newValue) {
        if (this.houseHold == null) {
            this.houseHold = newValue;
        } else houseHold += newValue;
    }

    private void addValueOrInitializeClothingValue(Double newValue) {
        if (this.clothing == null) {
            this.clothing = newValue;
        } else clothing += newValue;
    }

    private void addValueOrInitializeBooksValue(Double newValue) {
        if (this.books == null) {
            this.books = newValue;
        } else books += newValue;
    }

    private void addValueOrInitializeDevelopmentValue(Double newValue) {
        if (this.development == null) {
            this.development = newValue;
        } else development += newValue;
    }

    private void addValueOrInitializeOtherValue(Double newValue) {
        if (this.other == null) {
            this.other = newValue;
        } else other += newValue;
    }

    private void addValueOrInitializeFeesValue(Double newValue) {
        if (this.fees == null) {
            this.fees = newValue;
        } else fees += newValue;
    }

    private void addValueOrInitializeTravelValue(Double newValue) {
        if (this.travel == null) {
            this.travel = newValue;
        } else travel += newValue;
    }

    private void addValueOrInitializeInvestmentsValue(Double newValue) {
        if (this.investments == null) {
            this.investments = newValue;
        } else investments += newValue;
    }

    private void addValueOrInitializeTransportValue(Double newValue) {
        if (this.transport == null) {
            this.transport = newValue;
        } else transport += newValue;
    }

    private void addValueOrInitializeHobbyValue(Double newValue) {
        if (this.hobby == null) {
            this.hobby = newValue;
        } else hobby += newValue;
    }

    private void addValueOrInitializeEventsValue(Double newValue) {
        if (this.events == null) {
            this.events = newValue;
        } else events += newValue;
    }

    private void addValueOrInitializeDiningValue(Double newValue) {
        if (this.dining == null) {
            this.dining = newValue;
        } else dining += newValue;
    }

    private void addValueOrInitializeHealthCareValue(Double newValue) {
        if (this.healthCare == null) {
            this.healthCare = newValue;
        } else healthCare += newValue;
    }

    private void addValueOrInitializeBillsValue(Double newValue) {
        if (this.bills == null) {
            this.bills = newValue;
        } else bills += newValue;
    }

    private void addValueOrInitializeGiftsValue(Double newValue) {
        if (this.gifts == null) {
            this.gifts = newValue;
        } else gifts += newValue;
    }

    public Double getFood() {
        return food;
    }

    public Double getHouseHold() {
        return houseHold;
    }

    public Double getClothing() {
        return clothing;
    }

    public Double getHealthCare() {
        return healthCare;
    }

    public Double getBills() {
        return bills;
    }

    public Double getTransport() {
        return transport;
    }

    public Double getHobby() {
        return hobby;
    }

    public Double getDining() {
        return dining;
    }

    public Double getEvents() {
        return events;
    }

    public Double getGifts() {
        return gifts;
    }

    public Double getInvestments() {
        return investments;
    }

    public Double getTravel() {
        return travel;
    }

    public Double getFees() {
        return fees;
    }

    public Double getDevelopment() {
        return development;
    }

    public Double getBooks() {
        return books;
    }

    public Double getOther() {
        return other;
    }

    public static String variableCostsTypes() {
        return "VariableCostsReal{" +
                ", food=" +
                ", houseHold=" +
                ", clothing=" +
                ", health=" +
                ", bills=" +
                ", transport=" +
                ", hobby=" +
                ", dining=" +
                ", events=" +
                ", gifts=" +
                ", investments=" +
                ", travel=" +
                ", fees=" +
                ", development=" +
                ", books=" +
                ", other=" +
                '}';
    }
}
