package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.dao.TransactionDao;
import com.bardab.budgettracker.gui.DoubleFormatter;
import com.bardab.budgettracker.model.Transaction;
import com.bardab.budgettracker.dao.TransactionInsertionManager;
import com.bardab.budgettracker.model.categories.VariableCosts;
import com.bardab.budgettracker.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class MainWindowFXController  {
    private TransactionDao transactionDao;

    TransactionInsertionManager transactionInsertionManager;

    public MainWindowFXController() {
        transactionDao = new TransactionDao(HibernateUtil.getInstance().getSessionFactory());
        transactionInsertionManager = new TransactionInsertionManager();

    }

    @FXML
    private BudgetForecastFXController budgetForecastController;
    @FXML
    TableView transactionTable;
    @FXML
    ObservableList<String> typeList = FXCollections.observableArrayList(VariableCosts.getTypes());
    @FXML
    ComboBox comboBoxTypes;
    @FXML
    DatePicker datePicker = new DatePicker();
    @FXML
    TextField valueField = new TextField();
    @FXML
    TextField descriptionField = new TextField();
    @FXML
    ContextMenu tableContextMenu;

    @FXML
    ProgressBar progressBar;



    public void init()  {


        listAllTransactions();
        comboBoxTypes.setItems(typeList);
        descriptionField.clear();
        datePicker.setValue(LocalDate.now());
        valueField.setTextFormatter(new DoubleFormatter().doubleFormatter());

//        budgetForecastController.init();

    }


    public void listAllTransactions(){
        Task<ObservableList<Transaction>> task = new GetAllTransactionsTask();
        transactionTable.itemsProperty().bind(task.valueProperty());
        transactionTable.onMouseClickedProperty();
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.visibleProperty().set(true);
        task.setOnSucceeded(e->progressBar.setVisible(false));
        task.setOnFailed(e->progressBar.setVisible(false));

        new Thread(task).start();
    }
    public void deleteTransaction(){
        transactionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Transaction transaction = (Transaction)transactionTable.getSelectionModel().getSelectedItem();
                transactionDao.deleteTransaction(transaction.getId());
                init();
            }
        });
        tableContextMenu.getItems().addAll(deleteMenuItem);
        transactionTable.setContextMenu(tableContextMenu);

    }
    public void addTransaction(){
        Transaction transaction = new Transaction();
        transaction.setType((String) comboBoxTypes.getValue());
        System.out.println((String) comboBoxTypes.getValue());
        transaction.setDescription(descriptionField.getText());
        transaction.setValue((Double.parseDouble(valueField.getText())));
        transaction.setTransactionDateAndMonthCode(datePicker.getValue());

        transactionInsertionManager.insertTransactionAndUpdateActiveCategories(transaction);
        init();

    }

    class GetAllTransactionsTask extends Task {
        @Override
        public ObservableList<Transaction> call() {
            System.out.println("List all transactions method's thread:  "+ Thread.currentThread().toString());
            return FXCollections.observableArrayList(transactionDao.displayAllTransactions("Transaction"));
        }
    }


}
