package com.bardab.budgettracker.gui.additional;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class DoubleFormatter  {

    public  TextFormatter<Double> doubleFormatter(){
        return new TextFormatter<>(converter, 0.0, filter);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    private static Pattern validEditingState = Pattern.compile("(([1-9][0-9]*)|0)?(\\.[0-9]{0,2})?");

    private static  UnaryOperator<TextFormatter.Change> filter = c -> {
        String text = c.getControlNewText();
        if (validEditingState.matcher(text).matches()) {
            return c ;
        } else {
            return null ;
        }
    };

    public static StringConverter<Double> converter = new StringConverter<Double>() {

        @Override
        public Double fromString(String s) {
            if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                return 0.0 ;
            } else {
                return Double.valueOf(s);
            }
        }


        @Override
        public String toString(Double d) {
            return d.toString();
        }
    };


    public static List<String> doubleListToStringList(List<Double> doubleList){
        List<String> stringList = new ArrayList<>();

        for(Double value:doubleList){
            stringList.add(value.toString());
        }
        return stringList;
    }


}
