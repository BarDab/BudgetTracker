package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.dao.TransactionDao;
import com.bardab.budgettracker.gui.additional.TooltippedTableCell;
import com.bardab.budgettracker.model.Transaction;
import com.bardab.budgettracker.model.additional.Category;
import com.bardab.budgettracker.model.additional.CategoryFormatter;
import com.bardab.budgettracker.util.HibernateUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionsFXController {

    private TransactionDao transactionDao;
    private MainWindowFXController mainWindowFXController;


    @FXML
    private List<Category> filteredList = new ArrayList<>();

    @FXML
    private MenuBar transactionsMenuBar;

    @FXML
    private Menu variableCategoriesMenu;

    @FXML
    private Menu fixedCategoriesMenu;

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
    private CustomMenuItem customMenuItemCategories;

    @FXML
    private CustomMenuItem customMenuItemFixed;
    @FXML
    private DatePicker transactionsDateFrom;
    @FXML
    private DatePicker transactionsDateTo;

    @FXML
    private ContextMenu tableContextMenu;

    private List<Transaction> transactionsList = new ArrayList<>();


    public void init(MainWindowFXController mainWindowFXController) {
        transactionDao = new TransactionDao(HibernateUtil.getInstance().getSessionFactory());

        this.mainWindowFXController = mainWindowFXController;
        transactionsDateFrom.setValue(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1));
        transactionsDateTo.setValue(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().lengthOfMonth()));

        createMenuCheckBoxes(variableCategoriesMenu, 5, customMenuItemCategories);

        updateTransactionsInTable();

        setToolTip();

        transactionsList.addAll(transactionDao.getAllTransactions(transactionsDateFrom.getValue(), transactionsDateTo.getValue(),filteredList));

    }


    public void setToolTip() {
        descriptionColumn.setCellFactory(TooltippedTableCell.forTableColumn());
    }


    public List<Transaction> getTransactionsList(){
        return this.transactionsList;
    }

    public void updatePieChartWithCurrentTransactions(){
        mainWindowFXController.updatePieChartWithDataFromTable();
    }
    public void updateBarChartWithCurrentTransactions(){
        mainWindowFXController.updateBarChartWithDataFromTable();
    }





    public void createMenuCheckBoxes(Menu menu, int numberOfRows, CustomMenuItem customMenuItem) {
        GridPane gp = new GridPane();
        gp.setOpacity(30);

        List<CheckBox> checkBoxes = new ArrayList<>();
        int x = 0;
        int y = 0;
        for (Category category : Category.values()) {
            CheckBox cb = new CheckBox(CategoryFormatter.getCategoryNameInPresentable(category));
            cb.setStyle("-fx-text-fill: white; -fx-font-size: 10;");
            cb.selectedProperty().setValue(true);
            filteredList.add(category);

            cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    if (t1) {
                        filteredList.add(category);
                    } else filteredList.remove(category);
                }
            });
            gp.add(cb, x, y);
            if (y < numberOfRows) {
                y++;
            } else {
                y = 0;
                x++;
            }
            checkBoxes.add(cb);
        }
        Button button = new Button("Unmark all");
        button.setOnAction(e -> markAllCheckBoxes(checkBoxes, button));
        GridPane.setHalignment(button, HPos.RIGHT);
        gp.add(button, x, y + 1);


        customMenuItem.setContent(gp);


        customMenuItem.setHideOnClick(false);


        menu.getItems().add(customMenuItem);



    }


    public void markAllCheckBoxes(List<CheckBox> checkBoxes, Button button) {
        if (button.getText().equals("Mark all")) {
            checkBoxes.forEach(e -> e.selectedProperty().setValue(true));
            button.setText("Unmark All");
        } else {
            checkBoxes.forEach(e -> e.selectedProperty().setValue(false));
            button.setText("Mark all");
        }
    }


    public void updateTransactionsInTable() {
        Task<ObservableList<Transaction>> task = new GetTransactionsFiltered(transactionsDateFrom.getValue(), transactionsDateTo.getValue(), filteredList);
        transactionTable.itemsProperty().bind(task.valueProperty());
        transactionTable.onMouseClickedProperty();
        new Thread(task).start();
    }

    public void deleteTransaction() {
        transactionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Transaction transaction = (Transaction) transactionTable.getSelectionModel().getSelectedItem();
                transactionDao.deleteTransaction(transaction.getId());
                updateTransactionsInTable();
            }
        });
        tableContextMenu.getItems().addAll(deleteMenuItem);
        transactionTable.setContextMenu(tableContextMenu);

    }


    class GetTransactionsFiltered extends Task {

        private LocalDate dateFrom;
        private LocalDate dateTo;
        private List<Category> categories;


        public GetTransactionsFiltered(LocalDate dateFrom, LocalDate dateTo, List<Category> categories) {
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
            this.categories = categories;
        }

        @Override
        public ObservableList<Transaction> call() {
            List<Transaction> transactions = transactionDao.getAllTransactions(dateFrom, dateTo, categories);
            transactionsList.removeAll(transactionsList);
            transactionsList.addAll(transactions);

            return FXCollections.observableArrayList(transactions);
        }
    }



}
