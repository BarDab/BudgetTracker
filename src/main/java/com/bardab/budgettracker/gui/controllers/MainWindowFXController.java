package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.model.additional.MonthCode;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

public class MainWindowFXController  {


    public MainWindowFXController() {
    }

    @FXML
    private BudgetFXController budgetController;
    @FXML
    private BalanceFXController balanceController;
    @FXML
    private NewTransactionFXController newTransactionController;
    @FXML
    private TransactionsFXController transactionsController;
    @FXML
    private ChartFXController chartController;
    @FXML
    private ChartDataFormFXController chartDataFormController;


    @FXML
    private VBox budgetForecastTabVBox;

    @FXML
    private VBox chartsTabVBox;

    @FXML
    private VBox investmentsTabVBox;

    @FXML
    private TabPane tabPane;

    @FXML
    private TabPane addingPane;

    public void init()  {
    chartController.init(this);
    chartDataFormController.init(this);


        budgetController.init();
        balanceController.init(MonthCode.createIntMonthCodeFromLocalDate(LocalDate.now()));
        newTransactionController.init();
        transactionsController.init();


//        tabPane.getSelectionModel().selectedItemProperty().addListener(
//                new ChangeListener<Tab>() {
//                    @Override
//                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
//                        tabPane.getSelectionModel().getSelectedItem().getGraphic().setStyle("-fx-background-color: #2f4f4f");
//                        for(Tab tab: tabPane.getTabs()){
//                            if(tab.getId()!=ov.getValue().getId()){
//                                tab.getGraphic().setStyle("-fx-background-color: #434343");
//
//                            }
//                        }
//                    }
//                });

        addingPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        addingPane.getSelectionModel().getSelectedItem().getGraphic().setStyle("-fx-background-color: #2f4f4f");
                        for(Tab tab: addingPane.getTabs()){
                            if(tab.getId()!=ov.getValue().getId()){
                                tab.getGraphic().setStyle("-fx-background-color: #434343");

                            }
                        }
                    }
                });


    }


    public void updatePieChart(LocalDate dateFrom, LocalDate dateTo, List<String> listOfCategories){
        this.chartController.updatePieChart(dateFrom,dateTo,listOfCategories);
    }
    public void updateLineChart(LocalDate dateFrom, LocalDate dateTo, List<String> listOfCategories){
        this.chartController.updateLineChart(dateFrom,dateTo,listOfCategories);
    }













}
