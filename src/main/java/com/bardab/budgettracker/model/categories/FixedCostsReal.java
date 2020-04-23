package com.bardab.budgettracker.model.categories;

import com.bardab.budgettracker.model.MonthlyBalance;

import javax.persistence.*;

@Entity
@Table(name = "FixedCostsReal",uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class FixedCostsReal {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private MonthlyBalance monthlyBalance;

    @Column (name = "monthCode")
    private String monthCode;
    @Column(name="electricity")
    private Double electricitySpending;
    @Column(name="gas")
    private Double gasSpending;
    @Column(name="rent")
    private Double rentSpending;
    @Column(name="otherSpending")
    private Double otherSpending;
    @Column(name="healthCare")
    private Double healthCareSpending;
    @Column(name="entertainment")
    private Double entertainmentSpending;
    @Column(name="hobby")
    private Double hobbySpending;
    @Column(name="transport")
    private Double transportSpending;
    @Column(name="mobileNetwork")
    private Double mobileNetworkSpending;
    @Column(name="internet")
    private Double internetSpending;



    public MonthlyBalance getMonthlyBalance() {
        return monthlyBalance;
    }

    public void setMonthlyBalance(MonthlyBalance monthlyBalance) {
        this.monthlyBalance = monthlyBalance;
    }

    public FixedCostsReal() {
        this.electricitySpending = 0.0;
        this.gasSpending = 0.0;
        this.rentSpending = 0.0;
        this.otherSpending = 0.0;
        this.healthCareSpending = 0.0;
        this.entertainmentSpending = 0.0;
        this.hobbySpending = 0.0;
        this.transportSpending = 0.0;
        this.mobileNetworkSpending = 0.0;
        this.internetSpending = 0.0;
    }

    public Double getSumOfFixedCosts(){
        return  this.internetSpending +
                this.mobileNetworkSpending +
                this.electricitySpending +
                this.gasSpending +
                this.rentSpending +
                this.otherSpending +
                this.healthCareSpending +
                this.entertainmentSpending +
                this.hobbySpending +
                this.transportSpending;
    }


    public void setMonthCode(String monthCode) {
        this.monthCode = monthCode;
    }

    public void addElectricitySpending(Double electricity) {
        this.electricitySpending += electricity;
    }

    public void addGasSpending(Double gas) {
        this.gasSpending += gas;
    }

    public void addRentSpending(Double rent) {
        this.rentSpending += rent;
    }

    public void addOtherSpending(Double other) {
        this.otherSpending += other;
    }

    public void addHealthCareSpending(Double healthCare) {
        this.healthCareSpending += healthCare;
    }

    public void addEntertainmentSpending(Double entertainment) {
        this.entertainmentSpending += entertainment;
    }

    public void addHobbySpending(Double hobby) {
        this.hobbySpending += hobby;
    }

    public void addTransportSpending(Double transport) {
        this.transportSpending += transport;
    }

    public void addMobileNetworkSpending(Double mobileNetwork) {
        this.mobileNetworkSpending += mobileNetwork;
    }

    public void addInternetSpending(Double internet) {
        this.internetSpending += internet;
    }

    public String getMonthCode() {
        return monthCode;
    }

    public Double getElectricitySpending() {
        return electricitySpending;
    }

    public Double getGasSpending() {
        return gasSpending;
    }

    public Double getRentSpending() {
        return rentSpending;
    }

    public Double getOtherSpending() {
        return otherSpending;
    }

    public Double getHealthCareSpending() {
        return healthCareSpending;
    }

    public Double getEntertainmentSpending() {
        return entertainmentSpending;
    }

    public Double getHobbySpending() {
        return hobbySpending;
    }

    public Double getTransportSpending() {
        return transportSpending;
    }

    public Double getMobileNetworkSpending() {
        return mobileNetworkSpending;
    }

    public Double getInternetSpending() {
        return internetSpending;
    }
}
