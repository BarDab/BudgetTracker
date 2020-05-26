package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.gui.ChartData;
import com.bardab.budgettracker.model.categories.Categories;
import com.bardab.budgettracker.model.categories.VariableExpenses;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChartFXController {

    private MainWindowFXController mainController;

    @FXML
    private PieChart categoriesPieChart;

    @FXML
    private TabPane chartArea;

    @FXML
    private Tab pieChartTab;

    @FXML
    private Tab lineChartTab;

    @FXML
    private CategoryAxis xAxis = new CategoryAxis();
    @FXML
    private NumberAxis yAxis = new NumberAxis();
    @FXML
    private LineChart<String, Double> lineChart;

    @FXML
    private ListView<String> categoriesListView = new ListView();




    private ObservableList categoriesList;
    private VariableExpenses variableExpenses;


    public void init(MainWindowFXController mainController) {
        this.mainController = mainController;
        variableExpenses = new VariableExpenses();
        variableExpenses.initializeFields(variableExpenses);
        categoriesList = FXCollections.observableArrayList((VariableExpenses.getPresentableCategoriesNames(variableExpenses)));
        categoriesListView.setItems(categoriesList);


        pieChartTab.setGraphic(tabHighlighting(createVBoxWithNamedLabel("Pie Chart"),true));
        lineChartTab.setGraphic(tabHighlighting(createVBoxWithNamedLabel("Line Chart"),false));
        tabsHighlight();
    }


    public void updatePieChart(LocalDate dateFrom, LocalDate dateTo, List<String> listOfCategories){
        List<String> camelCaseCategories = new ArrayList();
        for (String category : listOfCategories) {
            camelCaseCategories.add(Categories.transformToCamelCase(category));
        }
        listOfCategories = FXCollections.observableArrayList(camelCaseCategories);

        this.categoriesPieChart.setData(ChartData.getSeriesForPieChart(dateFrom,dateTo,listOfCategories));
        this.categoriesPieChart.setLegendSide(Side.BOTTOM);

    }

    public void updateLineChart(LocalDate dateFrom, LocalDate dateTo, List<String> listOfCategories) {

        List<String> camelCaseCategories = new ArrayList();
        for (String category : listOfCategories) {
            camelCaseCategories.add(Categories.transformToCamelCase(category));
        }
        listOfCategories = FXCollections.observableArrayList(camelCaseCategories);

        List<XYChart.Series<String, Double>> list = ChartData.getListOfSeriesForLineChart(dateFrom, dateTo, listOfCategories);

        lineChart.getData().setAll(list);
    }




    public VBox createVBoxWithNamedLabel(String labelText){
        VBox vBox = new VBox();
        Label label = new Label(labelText);
        vBox.getChildren().add(label);

        return vBox;
    }

    public VBox tabHighlighting (VBox vBox,boolean isHighlighted){

        
        if(isHighlighted){
            HBox hBox = new HBox();
            hBox.setMinHeight(1);
            hBox.setStyle("-fx-background-color: #75c5fa");
            vBox.setStyle("-fx-background-color:#4e5254;");
            vBox.getChildren().add(hBox);
        }
        else {
            vBox.setStyle("-fx-background-color:#2B2B2B;");

        }

        return vBox;
    }


    public void tabsHighlight(){



            chartArea.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                    VBox vBox = (VBox) chartArea.getSelectionModel().getSelectedItem().getGraphic();
                    chartArea.getSelectionModel().getSelectedItem().setGraphic(tabHighlighting(vBox,true));
                    for(Tab tab: chartArea.getTabs()){
                            if(tab.getId()!=ov.getValue().getId()){
                                tab.setGraphic(tabHighlighting((VBox) tab.getGraphic(),false));
                            }
                        }
                    }
                });
    }

}
