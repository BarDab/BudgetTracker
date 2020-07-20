package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.gui.additional.ChartData;
import com.bardab.budgettracker.model.Transaction;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class MainWindowFXController {


    private Stage stage;

    public MainWindowFXController() {


    }


    private double xOffset;
    private double yOffset;



    @FXML
    private BudgetFXController budgetController;
    @FXML
    private NewTransactionFXController newTransactionController;
    @FXML
    private TransactionsFXController transactionsController;
    @FXML
    private ChartFXController chartController;
    @FXML
    private SpendingManagerFXController spendingManagerController;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Pane mainPane;


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

    public void init(Stage stage) {
        chartController.init(this);
        budgetController.init(this);
        newTransactionController.init(this);
        transactionsController.init(this);
        spendingManagerController.init();

        this.stage = stage;

        moveWindowByMouseDragging(stage);
        updatePieChartWithDataFromTable();
        updateBarChartWithDataFromTable();

    }


    public Pane getMainPane() {
        return mainPane;
    }

    public void moveWindowByMouseDragging(Stage primaryStage) {

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

    public void updatePieChartWithDataFromTable() {

        List<Transaction> transactions = this.transactionsController.getTransactionsList();
        LocalDate dateFrom = ChartData.getLatestDate(transactions);
        LocalDate dateTo = ChartData.getEarliestDate(transactions);
        this.chartController.updatePieChartWithDataFromTable(dateFrom, dateTo, transactions, mainPane);
        this.chartController.updatePieChartTitle(dateFrom, dateTo);
    }

    public void updateBarChartWithDataFromTable() {
        List<Transaction> transactions = this.transactionsController.getTransactionsList();
        LocalDate dateFrom = ChartData.getLatestDate(transactions);
        LocalDate dateTo = ChartData.getEarliestDate(transactions);

        this.chartController.updateBarChartWithDataFromTable(dateFrom, dateTo, transactions);
    }




    public void updateTransactionsTable() {
        this.transactionsController.updateTransactionsInTable();

    }

    public void updateMonthlyBalance() {
        this.budgetController.update();
    }


    public void minimizeWindow() {
        stage.setIconified(true);
    }

    public void maximizeWindow() {
        if (stage.isMaximized()) stage.setMaximized(false);
        else stage.setMaximized(true);
    }

    public void closeApp() {
        System.exit(0);
    }


}
