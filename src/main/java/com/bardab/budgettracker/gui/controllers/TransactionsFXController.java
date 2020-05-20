package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.dao.TransactionDao;
import com.bardab.budgettracker.model.Transaction;
import com.bardab.budgettracker.model.additional.MonthCode;
import com.bardab.budgettracker.model.categories.Categories;
import com.bardab.budgettracker.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class TransactionsFXController {

    private TransactionDao transactionDao;


    public TransactionsFXController() {
        transactionDao = new TransactionDao(HibernateUtil.getInstance().getSessionFactory());


    }


    @FXML
    private ToggleGroup categoriesButtons = new ToggleGroup();
    @FXML
    private TableView transactionTable;
    @FXML
    private TableColumn dateColumn;
    @FXML
    private TableColumn categoryColumn;
    @FXML
    private TableColumn valueColumn;
    @FXML
    private TableColumn descriptionColumn;

    @FXML
    private Label transactionTableLabel;

    @FXML
    private ContextMenu tableContextMenu;

    private Integer monthCode = MonthCode.createIntMonthCodeFromLocalDate(LocalDate.now());

//    public void init()  {
//        listAllTransactions(monthCode);
//        transactionTableLabel.setText(MonthCode.monthYear(monthCode));
//    }
    public void updateList(){
        listAllTransactions(monthCode);
    }


    public void nextMonthTransactions(){
        this.monthCode = MonthCode.getNextMonthCode(monthCode);
        listAllTransactions(this.monthCode);
        transactionTableLabel.setText(MonthCode.monthYear(monthCode));

    }
    public void previousMonthTransactions(){
        this.monthCode = MonthCode.getPreviousMonthCode(monthCode);
        listAllTransactions(this.monthCode);
        transactionTableLabel.setText(MonthCode.monthYear(monthCode));
    }


    public void listAllTransactions(Integer monthCode){
        Task<ObservableList<Transaction>> task = new GetAllTransactionsTask(monthCode);
        transactionTable.itemsProperty().bind(task.valueProperty());
        transactionTable.onMouseClickedProperty();
//        progressBar.progressProperty().bind(task.progressProperty());
//        progressBar.visibleProperty().set(true);
//        task.setOnSucceeded(e->progressBar.setVisible(false));
//        task.setOnFailed(e->progressBar.setVisible(false));

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
//                init();
            }
        });
        tableContextMenu.getItems().addAll(deleteMenuItem);
        transactionTable.setContextMenu(tableContextMenu);

    }


    class GetAllTransactionsTask extends Task {

        private Integer monthCode;
        public GetAllTransactionsTask(Integer monthCode) {
            this.monthCode = monthCode;
        }

        @Override
        public ObservableList<Transaction> call() {
            System.out.println("List all transactions method's thread:  "+ Thread.currentThread().toString());
            List<Transaction> transactions = transactionDao.getAllTransactions(monthCode);
            //very ugly not proud of it, needs change in the future - setting different category casing convention
            // here categories names are set with normal format, and when adding to db I use camelCase convention
            transactions.forEach(e->e.setPresentableCategory(Categories.getPresentableCategoryName(e.getCategory())));

            return FXCollections.observableArrayList(transactions);
        }
    }
}
