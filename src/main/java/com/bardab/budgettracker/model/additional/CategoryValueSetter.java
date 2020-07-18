package com.bardab.budgettracker.model.additional;

import java.util.HashMap;

public interface CategoryValueSetter {


    void initializeCategoryValues();
    void updateCategoryValue(Category category, Double value);
    Double getCategoryValue(Category category);
    HashMap<Category, Double> getMapOfCategoriesWithValues();
}
