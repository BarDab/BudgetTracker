package com.bardab.budgettracker.model;


import com.bardab.budgettracker.model.additional.Category;
import com.bardab.budgettracker.model.additional.CategoryValueSetter;
import com.vladmihalcea.hibernate.type.basic.YearMonthDateType;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Entity
@Table(name = "ActualExpenses")
@TypeDef(
        typeClass = YearMonthDateType.class,
        defaultForType = YearMonth.class
)
public class ActualExpenses implements Serializable, CategoryValueSetter {


    public ActualExpenses() {
    }

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Actual actual;


    @Id
    private Long id;

    @Column(
            name = "yearMonth",
            columnDefinition = "date"
    )
    private YearMonth yearMonth;


    @Column(name = "food")
    private Double food;
    @Column(name = "household")
    private Double houseHold;
    @Column(name = "clothing")
    private Double clothing;
    @Column(name = "healthCare")
    private Double healthCare;
    @Column(name = "bills")
    private Double bills;
    @Column(name = "transport")
    private Double transport;
    @Column(name = "hobby")
    private Double hobby;
    @Column(name = "dining")
    private Double dining;
    @Column(name = "debt")
    private Double debt;
    @Column(name = "other")
    private Double other;


    public void initializeCategoryValues() {
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getType().equals(Double.class)) {
                    if (field.get(this) == null) {
                        field.set(this, 0.0);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.getMessage();
        }
    }

    public void updateCategoryValue(Category category, Double value) {

        switch (category) {
            case DEBT:
                this.debt += value;
                break;
            case FOOD:
                this.food += value;
                break;
            case CLOTHING:
                this.clothing += value;
                break;
            case HOBBY:
                this.hobby += value;
                break;
            case DINING:
                this.dining += value;
                break;
            case BILLS:
                this.bills += value;
                break;
            case OTHER:
                this.other += value;
                break;
            case HEALTHCARE:
                this.healthCare += value;
                break;
            case TRANSPORT:
                this.transport += value;
                break;
        }
    }

    public Double getCategoryValue(Category category) {

        switch (category) {
            case DEBT:
                return this.debt;

            case FOOD:
                return this.food;

            case CLOTHING:
                return this.clothing;

            case HOBBY:
                return this.hobby;

            case DINING:
                return this.dining;

            case BILLS:
                return this.bills;

            case HEALTHCARE:
                return this.healthCare;

            case TRANSPORT:
                return this.transport;

        }
        return this.other;
    }


    public HashMap<Category, Double> getMapOfCategoriesWithValues() {
        HashMap<Category, Double> mapOfCategoriesWithValues = new LinkedHashMap();
        for (Category category : Category.expenses()) {
            mapOfCategoriesWithValues.put(category,getCategoryValue(category));
        }

        return mapOfCategoriesWithValues;
    }




    public Actual getActual() {
        return actual;
    }

    public void setActual(Actual actual) {
        this.actual = actual;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }
}
