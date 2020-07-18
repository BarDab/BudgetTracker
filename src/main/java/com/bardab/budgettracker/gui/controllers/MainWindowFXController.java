package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.gui.additional.ChartData;
import com.bardab.budgettracker.model.Transaction;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
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
    private NewBudgetFXController newBudgetController;


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

    public void newBudget(){

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        dialog.setTitle("New Budget");
//
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(getClass().getResource("C:/Users/Admin/JavaProjects/BudgetTracker/target/classes/com/bardab/budgettracker/gui/fxmls/newBudget.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxmls/newBudget.fxml"));
        String cwd = System.getProperty("user.dir");
        System.out.println( this.getClass());
        System.out.println(cwd);
        System.out.println( getClass().getResource("fxmls/newBudget.fxml"));

        System.out.println(fxmlLoader.getLocation());


        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }
        catch (IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

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
