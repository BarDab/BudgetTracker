package com.bardab.budgettracker.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table (name = "FixedCosts")
public class FixedCosts {

    @Id
    @Column(name="ID")
    private Long id;

    @OneToOne
    @MapsId
    private BudgetForecast budgetForecast;

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

    public BudgetForecast getBudgetForecast() {
        return budgetForecast;
    }

    public void setBudgetForecast(BudgetForecast budgetForecast) {
        this.budgetForecast = budgetForecast;
    }

    public void setTransport(Double transport) {
        if(transport>0.0){
            this.transport = transport;
        }
        else this.transport = 0.0;
    }

    public Double getSum(){
        return this.electricity+
        this.gas+
        this.rent+
        this.otherUtilities+
        this.healthCare +
        this.entertainment+
        this.hobby+
        this.transport;
    }

    public void setDefaultValues(){
        this.electricity =0.0;
        this.gas =0.0;
        this.rent =0.0;
        this.otherUtilities =0.0;
        this.healthCare =0.0;
        this.entertainment =0.0;
        this.hobby =0.0;
        this.transport =0.0;
    }

    @Override
    public String toString() {
        return "FixedCosts{" +

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
    public LinkedHashMap<String,Double> getFixedCostsTypesWithValues(){
        LinkedHashMap<String,Double> typesWithLastValue = new LinkedHashMap<>();
        String typesWithValues = toString();


        Pattern valuePattern = Pattern.compile("(\\d+)((\\.\\d{1,2}))|null");
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

            System.out.println(name+" " +value);
            typesWithLastValue.put(name,value);
        }
        return typesWithLastValue;
    }
    public ArrayList<String> getFixedCostsTypesWithValuesList(){
        ArrayList<String> typesListWithValues = new ArrayList<>();
        getFixedCostsTypesWithValues().entrySet().forEach(e->typesListWithValues.add(e.getKey()+" "+e.getValue()));
        return typesListWithValues;
    }


}
