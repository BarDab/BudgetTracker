package com.bardab.budgettracker.model.categories;

import com.bardab.budgettracker.model.Transaction;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Categories {

    static String income = "income";
    static String investments = "investments";


    static List<String> categoriesNames(){
        return null;
    }


    public static boolean validateType(String type, List<String> listOfCategories){
        if (type.equals(income)) return true;
        if (type.equals(investments)) return true;
        if (type.matches("(.+)fixed")) {
            for (String t : listOfCategories) {
                if (type.equalsIgnoreCase(t)) {
                    return true;
                }
            }
        }
        for (String t : listOfCategories) {
            if (type.equalsIgnoreCase(t)) {
                return true;
            }
        }
        return false;
    }
    public static HashMap<String, Double> getMapOfCategories(Object object) {
        HashMap<String,Double> map= new HashMap<>();

        try{
            for(Field field:object.getClass().getDeclaredFields()){
                field.setAccessible(true);
                if(field.getType().equals(Double.class)){
                    if((Double) field.get(object)==null){map.put(field.getName(),0.0);}
                    else
                    map.put(field.getName(),(Double) field.get(object));
                }
            }
        }
        catch (IllegalAccessException e){
            e.getMessage();
        }
        return map;
    }
    public void initializeFields(Object object){
        try{
            for(Field field: object.getClass().getDeclaredFields()){
                field.setAccessible(true);
                if(field.getType().equals(Double.class)){
                    if(field.get(object)==null){
                        field.set(object,0.0);
                    }
                }
            }
        }
        catch (IllegalAccessException e){
            e.getMessage();
        }
    }

    public void updateValue(Object object,Transaction transaction){
        try{
            for(Field field: object.getClass().getDeclaredFields()){
                field.setAccessible(true);
                if(field.getType().equals(Double.class)&&field.getName().equals(transaction.getCategory())){
                        field.set(object,transaction.getValue());
                }
            }
        }
        catch (IllegalAccessException e){
            e.getMessage();
        }

    }


    public void updateValue(Object object, String category, Double value){
        try{
            for(Field field: object.getClass().getDeclaredFields()){
                field.setAccessible(true);
                if(field.getType().equals(Double.class)&&field.getName().equals(category)){
                    field.set(object,value);
                }
            }
        }
        catch (IllegalAccessException e){
            e.getMessage();
        }

    }

    public static ArrayList<String> getCategoriesNames(Object object){
        ArrayList arrayList = new ArrayList();
        for(Field field: object.getClass().getDeclaredFields()){
            field.setAccessible(true);
            if(field.getType().equals(Double.class)){
                arrayList.add(field.getName());
            }
        }
        return arrayList;

    }

    public static List<String> getPresentableCategoriesWithValues(Object object){
        List<String> list = getCategoriesWithValues(object);

        for(int i = 0; i<list.size();i++){
            String preChanged = list.get(i);
            String name1 = preChanged.replaceAll("(\\p{Lu})"," "+"$1");
            String name2 = name1.substring(0,1).toUpperCase() + name1.substring(1);
            list.set(i,name2);
        }
        return list;
    }
    public static List<String> getPresentableCategoriesNames(Object object){
        List<String> list = getCategoriesNames(object);

        for(int i = 0; i<list.size();i++){
            String preChanged = list.get(i);
            String name1 = preChanged.replaceAll("(\\p{Lu})"," "+"$1");
            String name2 = name1.substring(0,1).toUpperCase() + name1.substring(1);
            list.set(i,name2);
        }
        return list;
    }

    public static String getPresentableCategoryName(String category){

            String name1 = category.replaceAll("(\\p{Lu})"," "+"$1");
            String name2 = name1.substring(0,1).toUpperCase() + name1.substring(1);
            return name2;
    }

    public static String transformToCamelCase(String category){
        String name1 = category.replaceAll("\\s","");
        category = name1.substring(0,1).toLowerCase() + name1.substring(1);
        return category;
    }



    public static List<String> getCategoriesWithValues(Object object){
        ArrayList<String> arrayList = new ArrayList<>();
        getMapOfCategories(object).forEach((e,f)-> arrayList.add(e+" "+f));
        return arrayList;
    }


    abstract Double getTotalValue();



}
    
    

