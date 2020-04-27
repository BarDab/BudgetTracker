package com.bardab.budgettracker.model.categories;

import com.bardab.budgettracker.model.MonthlyBalance;
import com.bardab.budgettracker.model.Transaction;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FixedCosts")
public class FixedCosts implements Serializable{

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


    public Integer getMonthCode() {
        return monthCode;
    }

    public void setMonthCode(Integer monthCode) {
        this.monthCode = monthCode;
    }

    public MonthlyBalance getMonthlyBalance() {
        return monthlyBalance;
    }

    public void setMonthlyBalance(MonthlyBalance monthlyBalance) {
        this.monthlyBalance = monthlyBalance;
    }

    public static List<String> getTypes() {
        List<String> transactionTypes = List.of("Rent","Electricity","Gas","HealthCare","Entertainment","Transport",
                "Hobby","MobileNetwork","Internet","Other");
        return transactionTypes;
    }



    public void updateWithTransaction(Transaction transaction) {
        this.monthCode = transaction.getMonthCode();

        String transactionType = transaction.getType();
        Double transactionValue = transaction.getValue();
        switch (transactionType) {
            case "electricityFixed":
                addElectricitySpending(transactionValue);
                break;
            case "gasFixed":
                addGasSpending(transactionValue);
                break;
            case "rentFixed":
                addRentSpending(transactionValue);
                break;
            case "otherFixed":
                addOtherSpending(transactionValue);
                break;
            case "healthCareFixed":
                addHealthCareSpending(transactionValue);
                break;
            case "entertainmentFixed":
                addEntertainmentSpending(transactionValue);
                break;
            case "hobbyFixed":
                addHobbySpending(transactionValue);
                break;
            case "transportFixed":
                addTransportSpending(transactionValue);
                break;
            case "mobileNetworkFixed":
                addMobileNetworkSpending(transactionValue);
                break;
            case "internetFixed":
                addInternetSpending(transactionValue);
                break;

        }
    }


    private void addElectricitySpending(Double electricity) {
        if (this.electricityFixed == null) {
            this.electricityFixed = electricity;
        } else
            this.electricityFixed += electricity;
    }

    private void addGasSpending(Double gas) {
        if (this.gasFixed == null) {
            this.gasFixed = gas;
        } else
            this.gasFixed += gas;
    }

    private void addRentSpending(Double rent) {
        if (this.rentFixed == null) {
            this.rentFixed = rent;
        } else
            this.rentFixed += rent;
    }

    private void addOtherSpending(Double other) {
        if (this.otherFixed == null) {
            this.otherFixed = other;
        } else
            this.otherFixed += other;
    }

    private void addHealthCareSpending(Double healthCare) {
        if (this.healthCareFixed == null) {
            this.healthCareFixed = healthCare;
        } else
            this.healthCareFixed += healthCare;
    }

    private void addEntertainmentSpending(Double entertainment) {
        if (this.entertainmentFixed == null) {
            this.entertainmentFixed = entertainment;
        } else
            this.entertainmentFixed += entertainment;
    }

    private void addHobbySpending(Double hobby) {
        if (this.hobbyFixed == null) {
            this.hobbyFixed = hobby;
        } else
            this.hobbyFixed += hobby;
    }

    private void addTransportSpending(Double transport) {
        if (this.transportFixed == null) {
            this.transportFixed = transport;
        } else
            this.transportFixed += transport;
    }

    private void addMobileNetworkSpending(Double mobileNetwork) {
        if (this.mobileNetworkFixed == null) {
            this.mobileNetworkFixed = mobileNetwork;
        } else
            this.mobileNetworkFixed += mobileNetwork;
    }

    private void addInternetSpending(Double internet) {
        if (this.internetFixed == null) {
            this.internetFixed = internet;
        } else
            this.internetFixed += internet;
    }


    public Double getElectricityFixed() {
        return electricityFixed;
    }

    public Double getGasFixed() {
        return gasFixed;
    }

    public Double getRentFixed() {
        return rentFixed;
    }

    public Double getOtherFixed() {
        return otherFixed;
    }

    public Double getHealthCareFixed() {
        return healthCareFixed;
    }

    public Double getEntertainmentSpending() {
        return entertainmentFixed;
    }

    public Double getHobbyFixed() {
        return hobbyFixed;
    }

    public Double getTransportFixed() {
        return transportFixed;
    }

    public Double getMobileNetworkFixed() {
        return mobileNetworkFixed;
    }

    public Double getInternetFixed() {
        return internetFixed;
    }


    public static String fixedCostsTypes() {
        return "FixedCostsReal{" +
                ", electricityFixed=" +
                ", gasFixed=" +
                ", rentFixed=" +
                ", otherFixed=" +
                ", healthCareFixed=" +
                ", entertainmentFixed=" +
                ", hobbyFixed=" +
                ", transportFixed=" +
                ", mobileNetworkFixed=" +
                ", internetFixed=" +
                '}';
    }
}
