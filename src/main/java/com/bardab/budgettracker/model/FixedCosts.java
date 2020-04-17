package com.bardab.budgettracker.model;

import javax.persistence.*;

@Entity
@Table (name = "FixedCosts",uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class FixedCosts {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID", nullable = false,unique = true,length = 11)
    private Long id;

    @Column (name = "monthCode")
    private String monthCode;
    @Column(name="electricity")
    private Double electricity;
    @Column(name="gas")
    private Double gas;
    @Column(name="rent")
    private Double rent;
    @Column(name="otherUtilities")
    private Double otherUtilities;
    @Column(name="healthCare")
    private Double healthCare;
    @Column(name="entertainment")
    private Double entertainment;
    @Column(name="hobby")
    private Double hobby;
    @Column(name="transport")
    private Double transport;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getElectricity() {
        return electricity;
    }

    public void setElectricity(Double electricity) {
        if(electricity>0.0){
            this.electricity = electricity;
        }
        else this.electricity = 0.0;
    }

    public String getMonthCode() {
        return monthCode;
    }

    public void setMonthCode(String monthCode) {
        if(validateMonthCode(monthCode))
        {
        this.monthCode = monthCode;
        }
    }
    public boolean validateMonthCode(String yearMonth){
        return MonthCode.validateMonthCode(yearMonth);
    }

    public Double getGas() {
        return gas;
    }

    public void setGas(Double gas) {
        if(gas>0.0){
        this.gas = gas;
        }
        else this.gas = 0.0;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        if(rent>0.0){
        this.rent = rent;
        }
        else this.rent = 0.0;
    }

    public Double getOtherUtilities() {
        return otherUtilities;
    }

    public void setOtherUtilities(Double otherUtilities) {
        if(otherUtilities>0.0){
        this.otherUtilities = otherUtilities;
        }
        else this.otherUtilities=0.0;
    }

    public Double getHealthCare() {
        return healthCare;
    }

    public void setHealthCare(Double healthCare) {
        if(healthCare>0.0){
        this.healthCare = healthCare;
        }
        else this.healthCare = 0.0;
    }

    public Double getEntertainment() {
        return entertainment;
    }

    public void setEntertainment(Double entertainment) {
        if(entertainment>0.0){
        this.entertainment = entertainment;
        }
        else this.entertainment = 0.0;
    }

    public Double getHobby() {
        return hobby;
    }

    public void setHobby(Double hobby) {
        if(hobby>0.0){
            this.hobby = hobby;
        }
        else this.hobby = 0.0;
    }

    public Double getTransport() {
        return transport;
    }

    public void setTransport(Double transport) {
        if(transport>0.0){
            this.transport = transport;
        }
        else this.transport = 0.0;
    }

    @Override
    public String toString() {
        return "FixedCosts{" +
                "id=" + id +
                ", monthCode='" + monthCode + '\'' +
                ", electricity=" + electricity +
                ", gas=" + gas +
                ", rent=" + rent +
                ", otherUtilities=" + otherUtilities +
                ", healthCare=" + healthCare +
                ", entertainment=" + entertainment +
                ", hobby=" + hobby +
                ", transport=" + transport +
                '}';
    }

//    @Override
//    public String toString() {
//        return "FixedCosts{" +
//                "  Electricity=" + electricity  +
//                ", Gas=" + gas +
//                ", Rent=" + rent +
//                ", OtherUtilities=" + otherUtilities +
//                ", HealthCare=" + healthCare +
//                ", Entertainment=" + entertainment +
//                ", Hobby=" + hobby +
//                ", Transport=" + transport +
//                '}';
//    }
}
