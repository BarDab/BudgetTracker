package com.bardab.budgettracker.model.additional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface VariableTypesConverter {


    public static LinkedHashMap<String, Double> toStringToHashMapConverting(String toString) {
        LinkedHashMap<String, Double> hashMap = new LinkedHashMap<>();

        Pattern valuePattern = Pattern.compile("(?<=((\\=)))(\\d+)((\\.\\d{1,2}))|null");
        Matcher valueMatcher = valuePattern.matcher(toString);

        Pattern namesPattern = Pattern.compile("(?<=((\\,)(\\s)))(\\w+)(?=((\\=)((\\d)|n)))");
        Matcher nameMatcher = namesPattern.matcher(toString);

        nameMatcher.reset();
        valueMatcher.reset();

        while (nameMatcher.find()) {
            String name = null;
            name = toString.substring(nameMatcher.start(), nameMatcher.end()).toLowerCase();

            Double value = null;

            valueMatcher.find();
            if (toString.substring(valueMatcher.start(), valueMatcher.end()).equals("null")) {
                value = 0.0;
            } else value = Double.valueOf(toString.substring(valueMatcher.start(), valueMatcher.end()));

            hashMap.put(name, value);
        }
        return hashMap;
    }

    public static ArrayList<String> toStringToLinkedListConvertingNamesAndValues(String toString) {
        ArrayList<String> typesListWithValues = new ArrayList<>();
        toStringToHashMapConverting(toString).entrySet().forEach(e -> typesListWithValues.add(e.getKey() + " " + e.getValue()));
        return typesListWithValues;
    }

    public static ArrayList<String> toStringToLinkedListConvertingOnlyNames(String toString) {
        ArrayList<String> typesListWithValues = new ArrayList<>();
        Pattern namesPattern = Pattern.compile("(?<=((\\,)(\\s)))(\\w+)(?=((\\=)|n))");
        Matcher nameMatcher = namesPattern.matcher(toString);

        nameMatcher.reset();
        while (nameMatcher.find()) {
            String name = null;
            name = toString.substring(nameMatcher.start(), nameMatcher.end()).toLowerCase();
            typesListWithValues.add(name);
        }
        return typesListWithValues;
    }


}
