package com.bardab.budgettracker.model.additional;

import java.util.ArrayList;
import java.util.List;

public class CategoryFormatter{

    public static Category getCategory(String categoryName){
        switch(categoryName.toLowerCase()){
            case "food": return Category.FOOD;
            case "health care": return Category.HEALTHCARE;
            case "hobby" : return Category.HOBBY;
            case "clothing": return Category.CLOTHING;
            case "debt": return Category.DEBT;
            case "bills": return Category.BILLS;
            case "transport": return Category.TRANSPORT;
            case "dining": return Category.DINING;
            case "entertainment": return Category.ENTERTAINMENT;
            case "savings": return Category.SAVINGS;
            case "income": return Category.INCOME;
        }
        return Category.OTHER;
    }
    public static String getCategoryNameInPresentable(Category category){
        switch(category){
            case FOOD: return  "Food";
            case HEALTHCARE: return  "Health Care";
            case HOBBY: return "Hobby";
            case CLOTHING: return  "Clothing";
            case BILLS: return "Bills";
            case TRANSPORT: return "Transport";
            case DEBT: return "Debt";
            case DINING: return "Dining";
            case ENTERTAINMENT: return "Entertainment";
            case SAVINGS: return "Savings";
            case INCOME: return "Income";
        }
        return "Other";
    }

    public static List<Category> getCategoriesFromPresentable(List<String> presentableCategoriesNames){
        List<Category> categories = new ArrayList<>();

        for(String presentableName:presentableCategoriesNames){
            categories.add(getCategory(presentableName));
        }


        return categories;

    }

    public static List<String> getExpenseCategoriesNamesInPresentable(){
        List<String> categories = new ArrayList();
        for(Category category:Category.values()){
            if(category.equals(Category.SAVINGS)||category.equals(Category.INCOME)){}
            else categories.add(getCategoryNameInPresentable(category));
        }
        return categories;
    }


    public static List<String> getAllCategoriesNamesInPresentable(){
        List<String> categories = new ArrayList();
        for(Category category:Category.values()){
            categories.add(getCategoryNameInPresentable(category));
        }
        return categories;
    }

}
