package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.gui.additional.ChartData;
import com.bardab.budgettracker.gui.additional.DoubleFormatter;
import com.bardab.budgettracker.model.Transaction;
import com.bardab.budgettracker.model.ActualExpenses;
import com.bardab.budgettracker.model.additional.CategoryFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.util.List;

public class ChartFXController {

    private MainWindowFXController mainController;

    @FXML
    private PieChart categoriesPieChart;

    @FXML
    private TabPane chartArea;





    @FXML
    private ObservableList<String> datesInSpecifiedPeriod;
    @FXML
    private CategoryAxis xAxis = new CategoryAxis();
    @FXML
    private NumberAxis yAxis = new NumberAxis();
    @FXML
    private StackedBarChart<String, Number> stackedBarChart;

    @FXML
    private ListView<String> categoriesListView = new ListView();




    private ObservableList categoriesList;
    private ActualExpenses actualExpenses;


    public void init(MainWindowFXController mainController) {
        this.mainController = mainController;

        xAxis.setAutoRanging(true);
        actualExpenses = new ActualExpenses();
        actualExpenses.initializeCategoryValues();
        categoriesList = FXCollections.observableArrayList((CategoryFormatter.getAllCategoriesNamesInPresentable()));
        categoriesListView.setItems(categoriesList);



    }


    public void updateBarChartWithDataFromTable(){
        this.mainController.updateBarChartWithDataFromTable();
    }

    public void updatePieChartWithDataFromTable(){
        this.mainController.updatePieChartWithDataFromTable();
    }


    public void updatePieChartTitle(LocalDate datesFrom, LocalDate datesTo){
       categoriesPieChart.setTitle(datesFrom.toString()+" to "+ datesTo.toString());
    }


    public void updatePieChartWithDataFromTable(LocalDate dateFrom, LocalDate dateTo,List<Transaction> transactions, Pane pane){
        this.categoriesPieChart.setData(ChartData.getSeriesForPieChart(transactions));
        this.categoriesPieChart.setLegendSide(Side.BOTTOM);
        addLabelOnHover(pane);
    }

//    public void updatePieChart(LocalDate dateFrom, LocalDate dateTo, List<String> listOfCategories, Pane pane){
//        List<String> camelCaseCategories = new ArrayList();
//        for (String category : listOfCategories) {
//            camelCaseCategories.add(Categories.transformToCamelCase(category));
//        }
//        listOfCategories = FXCollections.observableArrayList(camelCaseCategories);
//
//        this.categoriesPieChart.setData(ChartData.getSeriesForPieChart(dateFrom,dateTo,listOfCategories));
//        this.categoriesPieChart.setLegendSide(Side.BOTTOM);
//        addLabelOnHover(pane);
//    }

    public void updateBarChartWithDataFromTable( LocalDate dateFrom,LocalDate dateTo,List<Transaction> transactions){
        xAxis.setCategories(FXCollections.observableArrayList(ChartData.getListOfDatesInSpecificTime(dateFrom,dateTo)));
        xAxis.getCategories().removeAll(xAxis.getCategories());
        xAxis.getCategories().addAll(FXCollections.observableArrayList(ChartData.getListOfDatesInSpecificTime(dateFrom,dateTo)));
        List<XYChart.Series<String, Number>> list = ChartData.getListOfSeriesForXYChart(transactions);
        stackedBarChart.getData().removeAll(stackedBarChart.getData());
        stackedBarChart.getData().addAll(list);
    }


//    public void updateBarChart(LocalDate dateFrom, LocalDate dateTo, List<String> listOfCategories) {
//
//        List<String> camelCaseCategories = new ArrayList();
//        for (String category : listOfCategories) {
//            camelCaseCategories.add(Categories.transformToCamelCase(category));
//        }
//        listOfCategories = FXCollections.observableArrayList(camelCaseCategories);
//        xAxis.setCategories(FXCollections.observableArrayList(ChartData.getListOfDatesInSpecificTime(dateFrom,dateTo)));
//        xAxis.getCategories().removeAll(xAxis.getCategories());
//        xAxis.getCategories().addAll(FXCollections.observableArrayList(ChartData.getListOfDatesInSpecificTime(dateFrom,dateTo)));
//
//
//        List<XYChart.Series<String, Number>> list = ChartData.getListOfSeriesForXYChart(dateFrom, dateTo, listOfCategories);
//        stackedBarChart.getData().removeAll(stackedBarChart.getData());
//        stackedBarChart.getData().addAll(list);
//    }







//    public VBox createVBoxWithNamedLabel(String labelText){
//        VBox vBox = new VBox();
//        Label label = new Label(labelText);
//        vBox.getChildren().add(label);
//
//        return vBox;
//    }

//    public VBox tabHighlighting (VBox vBox,boolean isHighlighted){
//        if(isHighlighted){
//            HBox hBox = new HBox();
//            hBox.setMinHeight(1);
//            hBox.setStyle("-fx-background-color: #75c5fa");
//            vBox.setStyle("-fx-background-color:#4e5254;");
//            vBox.getChildren().add(hBox);
//        }
//        else {
//            vBox.setStyle("-fx-background-color:#2B2B2B;");
//
//        }
//
//        return vBox;
//    }

//
//    public void tabsHighlight(){
//
//
//            chartArea.getSelectionModel().selectedItemProperty().addListener(
//                new ChangeListener<Tab>() {
//                    @Override
//                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
//                    VBox vBox = (VBox) chartArea.getSelectionModel().getSelectedItem().getGraphic();
//                    chartArea.getSelectionModel().getSelectedItem().setGraphic(tabHighlighting(vBox,true));
//                    for(Tab tab: chartArea.getTabs()){
//                            if(tab.getId()!=ov.getValue().getId()){
//                                tab.setGraphic(tabHighlighting((VBox) tab.getGraphic(),false));
//                            }
//                        }
//                    }
//                });


    public void addLabelOnHover(Pane pane){


        Label pieSliceValue = new Label("");
        double[] arr = {0.0};

        pane.getChildren().add(pieSliceValue);

        for (final PieChart.Data data : categoriesPieChart.getData()) {
            arr[0]+=data.getPieValue();
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {



                            data.getNode().setScaleX(1.5);
                            data.getNode().setScaleY(1.5);
                            data.getNode().setScaleZ(1.5);

                            pieSliceValue.setTextFill(Color.WHITE);

                            pieSliceValue.setTranslateX(e.getSceneX());
                            pieSliceValue.setTranslateY(e.getSceneY());
                            pieSliceValue.setText(String.valueOf(DoubleFormatter.round(data.getPieValue(),1)) +"zł");

                        }
                    });
            data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED,e->
            {
                pieSliceValue.setTextFill(Color.TRANSPARENT);
                data.getNode().setScaleX(1);
                data.getNode().setScaleY(1);
                data.getNode().setScaleZ(1);
            });



    }}

}
