package com.bardab.budgettracker.model.categories;

import com.bardab.budgettracker.model.BudgetForecast;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table (name = "FixedCostsForecast",uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class FixedCostsForecast  {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private BudgetForecast budgetForecast;

    @Column (name = "monthCode")
    private String monthCode;
    @Column(name="electricity")
    private Double electricity;
    @Column(name="gas")
    private Double gas;
    @Column(name="rent")
    private Double rent;
    @Column(name="otherSpending")
    private Double other;
    @Column(name="healthCare")
    private Double healthCare;
    @Column(name="entertainment")
    private Double entertainment;
    @Column(name="hobby")
    private Double hobby;
    @Column(name="transport")
    private Double transport;
    @Column(name="mobileNetwork")
    private Double mobileNetwork;
    @Column(name="internet")
    private Double internet;



    public void setDefaultValues(Double value){
        this.electricity = value;
        this.gas = value;
        this.rent = value;
        this.other = value;
        this.healthCare = value;
        this.entertainment = value;
        this.hobby = value;
        this.transport = value;
        this.mobileNetwork = value;
        this.internet = value;
    }

    public Double getSumOfFixedCosts(){
        return  this.internet +
                this.mobileNetwork +
                this.electricity +
                this.gas +
                this.rent +
                this.other +
                this.healthCare +
                this.entertainment +
                this.hobby +
                this.transport;
    }
    public BudgetForecast getBudgetForecast() {
        return budgetForecast;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBudgetForecast(BudgetForecast budgetForecast) {
        this.budgetForecast = budgetForecast;
    }

    public void setMonthCode(String monthCode) {
        this.monthCode = monthCode;
    }

    public void setElectricity(Double electricity) {
        this.electricity = electricity;
    }

    public void setGas(Double gas) {
        this.gas = gas;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public void setOther(Double other) {
        this.other = other;
    }

    public void setHealthCare(Double healthCare) {
        this.healthCare = healthCare;
    }

    public void setEntertainment(Double entertainment) {
        this.entertainment = entertainment;
    }

    public void setHobby(Double hobby) {
        this.hobby = hobby;
    }

    public void setTransport(Double transport) {
        this.transport = transport;
    }

    public void setMobileNetwork(Double mobileNetwork) {
        this.mobileNetwork = mobileNetwork;
    }

    public void setInternet(Double internet) {
        this.internet = internet;
    }

    public void addElectricitySpending(Double electricity) {
        this.electricity += electricity;
    }

    public void addGasSpending(Double gas) {
        this.gas += gas;
    }

    public void addRentSpending(Double rent) {
        this.rent += rent;
    }

    public void addOtherSpending(Double other) {
        this.other += other;
    }

    public void addHealthCareSpending(Double healthCare) {
        this.healthCare += healthCare;
    }

    public void addEntertainmentSpending(Double entertainment) {
        this.entertainment += entertainment;
    }

    public void addHobbySpending(Double hobby) {
        this.hobby += hobby;
    }

    public void addTransportSpending(Double transport) {
        this.transport += transport;
    }

    public void addMobileNetworkSpending(Double mobileNetwork) {
        this.mobileNetwork += mobileNetwork;
    }

    public void addInternetSpending(Double internet) {
        this.internet += internet;
    }

    public String getMonthCode() {
        return monthCode;
    }

    public Double getElectricity() {
        return electricity;
    }

    public Double getGas() {
        return gas;
    }

    public Double getRent() {
        return rent;
    }

    public Double getOther() {
        return other;
    }

    public Double getHealthCare() {
        return healthCare;
    }

    public Double getEntertainment() {
        return entertainment;
    }

    public Double getHobby() {
        return hobby;
    }

    public Double getTransport() {
        return transport;
    }

    public Double getMobileNetwork() {
        return mobileNetwork;
    }

    public Double getInternet() {
        return internet;
    }


    @Override
    public String toString() {
        return "FixedCostsForecast{" +
                ", electricity=" + electricity +
                ", gas=" + gas +
                ", rent=" + rent +
                ", other=" + other +
                ", healthCare=" + healthCare +
                ", entertainment=" + entertainment +
                ", hobby=" + hobby +
                ", transport=" + transport +
                ", mobileNetwork=" + mobileNetwork +
                ", internet=" + internet +
                '}';
    }



    public LinkedHashMap<String,Double> getFixedCostsTypesWithValuesFromToStringMethod(){
        LinkedHashMap<String,Double> typesWithLastValue = new LinkedHashMap<>();
        String typesWithValues = toString();


        Pattern valuePattern = Pattern.compile("(?<=((\\=)))(\\d+)((\\.\\d{1,2}))|null");
        Matcher valueMatcher = valuePattern.matcher(typesWithValues);

        Pattern namesPattern = Pattern.compile("(?<=((\\,)(\\s)))(\\w+)(?=((\\=)((\\d)|n)))");
        Matcher nameMatcher = namesPattern.matcher(typesWithValues);

        nameMatcher.reset();
        valueMatcher.reset();

        while(nameMatcher.find()){
            String name = null;
            name = typesWithValues.substring(nameMatcher.start(),nameMatcher.end());

            Double value = null;

            valueMatcher.find();
            if(typesWithValues.substring(valueMatcher.start(),valueMatcher.end()).equals("null")){
                value =0.0;
            }
            else value = Double.valueOf(typesWithValues.substring(valueMatcher.start(),valueMatcher.end()));

            typesWithLastValue.put(name,value);
        }
        return typesWithLastValue;
    }
    public ArrayList<String> getFixedCostsTypesWithValuesList(){
        ArrayList<String> typesListWithValues = new ArrayList<>();
        getFixedCostsTypesWithValuesFromToStringMethod().entrySet().forEach(e->typesListWithValues.add(e.getKey()+" "+e.getValue()));
        return typesListWithValues;
    }


}
