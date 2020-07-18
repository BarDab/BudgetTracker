package com.bardab.budgettracker.gui;

import com.bardab.budgettracker.gui.additional.DoubleFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DoubleFormatterTest {

    DoubleFormatter doubleFormatter;
    @BeforeEach
    void doubleFormatterInstance(){
        doubleFormatter = new DoubleFormatter();
    }
    @Test
    @DisplayName("Formatter support only values which are positive and accepts only currency format i.e 0.10, 1.11, 1000.33")
    void doubleFormatter() {
//        TextFormatter<Double> textFormatter = new TextFormatter<Double>(1,);
    }
}