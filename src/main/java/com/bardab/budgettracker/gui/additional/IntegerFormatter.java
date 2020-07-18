package com.bardab.budgettracker.gui.additional;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class IntegerFormatter {


    public  TextFormatter<Integer> integerFormatter(){
        return new TextFormatter<Integer>(converter, 0, filter);
    }


    private static Pattern validEditingState = Pattern.compile("(([1-9][0-9]*)|0)?");

    private static UnaryOperator<TextFormatter.Change> filter = c -> {
        String text = c.getControlNewText();
        if (validEditingState.matcher(text).matches()) {
            return c ;
        } else {
            return null ;
        }
    };

    public static StringConverter<Integer> converter = new StringConverter<Integer>() {

        @Override
        public Integer fromString(String s) {
            if (s.isEmpty() || "-".equals(s)) {
                return 0 ;
            } else {
                return Integer.valueOf(s);
            }
        }


        @Override
        public String toString(Integer d) {
            return d.toString();
        }
    };


    public static List<String> IntegerListToStringList(List<Integer> IntegerList){
        List<String> stringList = new ArrayList<>();

        for(Integer value:IntegerList){
            stringList.add(value.toString());
        }
        return stringList;
    }


}

