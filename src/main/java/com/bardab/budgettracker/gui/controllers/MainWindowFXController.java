package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.model.additional.MonthCode;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class MainWindowFXController  {


    public MainWindowFXController() {


    }


    private double xOffset;
    private double yOffset;

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
    private MenuBar menuBar;

    @FXML

    private BorderPane mainWindow;

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

    public void init(Stage stage)  {
    chartController.init(this);
    chartDataFormController.init(this);


        budgetController.init();
        balanceController.init(MonthCode.createIntMonthCodeFromLocalDate(LocalDate.now()));
        newTransactionController.init();
        transactionsController.init();


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/fxmls/mainWindow.fxml"));






//        addingPane.getSelectionModel().selectedItemProperty().addListener(
//                new ChangeListener<Tab>() {
//                    @Override
//                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
//                        addingPane.getSelectionModel().getSelectedItem().getGraphic().setStyle("-fx-background-color: #2B2B2B;");
//                        for(Tab tab: addingPane.getTabs()){
//                            if(tab.getId()!=ov.getValue().getId()){
//                                tab.getGraphic().setStyle("-fx-background-color:#3C3F41;");
//
//                            }
//                        }
//                    }
//                });

    moveWindowByMouseDragging(stage);
    }


    public void moveWindowByMouseDragging(Stage primaryStage){

        menuBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = primaryStage.getX() - event.getScreenX();
                yOffset = primaryStage.getY() - event.getScreenY();
            }
        });

        menuBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() + xOffset);
                primaryStage.setY(event.getScreenY() + yOffset);
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
