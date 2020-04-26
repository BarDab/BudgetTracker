package com.bardab.budgettracker.model.categories;

import com.bardab.budgettracker.model.MonthlyBalance;

import javax.persistence.*;

@Entity
@Table(name = "FixedCostsReal")
public class FixedCostsReal {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private MonthlyBalance monthlyBalance;

    @Column (name = "monthCode")
    private String monthCode;
    @Column(name="electricity_fixed")
    private Double electricityFixed;
    @Column(name="gas_fixed")
    private Double gasFixed;
    @Column(name="rent_fixed")
    private Double rentFixed;
    @Column(name="other_fixed")
    private Double otherFixed;
    @Column(name="healthCare_fixed")
    private Double healthCareFixed;
    @Column(name="entertainment_fixed")
    private Double entertainmentFixed;
    @Column(name="hobby_fixed")
    private Double hobbyFixed;
    @Column(name="transport_fixed")
    private Double transportFixed;
    @Column(name="mobileNetwork_fixed")
    private Double mobileNetworkFixed;
    @Column(name="internet_fixed")
    private Double internetFixed;



    public MonthlyBalance getMonthlyBalance() {
        return monthlyBalance;
    }

    public void setMonthlyBalance(MonthlyBalance monthlyBalance) {
        this.monthlyBalance = monthlyBalance;
    }

    public FixedCostsReal() {

        this.gasFixed = 0.0;
        this.rentFixed = 0.0;
        this.otherFixed = 0.0;
        this.healthCareFixed = 0.0;
        this.entertainmentFixed = 0.0;
        this.hobbyFixed = 0.0;
        this.transportFixed = 0.0;
        this.mobileNetworkFixed = 0.0;
        this.internetFixed = 0.0;
    }

    public Double getSumOfFixedCosts(){
        return  this.internetFixed +
                this.mobileNetworkFixed +
                this.electricityFixed +
                this.gasFixed +
                this.rentFixed +
                this.otherFixed +
                this.healthCareFixed +
                this.entertainmentFixed +
                this.hobbyFixed +
                this.transportFixed;
    }


    public void setMonthCode(String monthCode) {
        this.monthCode = monthCode;
    }

    public void addElectricitySpending(Double electricity) {
        if(this.electricityFixed==null){
            this.electricityFixed = electricity;
        }
        else
            this.electricityFixed += electricity;
    }

    public void addGasSpending(Double gas) {
        if(this.gasFixed==null){
            this.gasFixed = gas;
        }
        else
        this.gasFixed += gas;
    }

    public void addRentSpending(Double rent) {
        if(this.rentFixed==null){
            this.rentFixed = rent;
        }
        else
        this.rentFixed += rent;
    }

    public void addOtherSpending(Double other) {
        if(this.otherFixed==null){
            this.otherFixed = other;
        }
        else
        this.otherFixed += other;
    }

    public void addHealthCareSpending(Double healthCare) {
        if(this.healthCareFixed==null){
            this.healthCareFixed = healthCare;
        }
        else
        this.healthCareFixed += healthCare;
    }

    public void addEntertainmentSpending(Double entertainment) {
        if(this.entertainmentFixed==null){
            this.entertainmentFixed = entertainment;
        }
        else
        this.entertainmentFixed += entertainment;
    }

    public void addHobbySpending(Double hobby) {
        if(this.hobbyFixed==null){
            this.hobbyFixed = hobby;
        }
        else
        this.hobbyFixed += hobby;
    }

    public void addTransportSpending(Double transport) {
        if(this.transportFixed==null){
            this.transportFixed = transport;
        }
        else
        this.transportFixed += transport;
    }

    public void addMobileNetworkSpending(Double mobileNetwork) {
        if(this.mobileNetworkFixed==null){
            this.mobileNetworkFixed = mobileNetwork;
        }
        else
        this.mobileNetworkFixed += mobileNetwork;
    }

    public void addInternetSpending(Double internet) {
        if(this.internetFixed==null){
            this.internetFixed = internet;
        }
        else
        this.internetFixed += internet;
    }

    public String getMonthCode() {
        return monthCode;
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
