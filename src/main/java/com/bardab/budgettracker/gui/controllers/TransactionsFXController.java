package com.bardab.budgettracker.gui.controllers;

import com.bardab.budgettracker.dao.TransactionDao;
import com.bardab.budgettracker.gui.TooltippedTableCell;
import com.bardab.budgettracker.model.Transaction;
import com.bardab.budgettracker.model.additional.MonthCode;
import com.bardab.budgettracker.model.categories.Categories;
import com.bardab.budgettracker.model.categories.FixedExpenses;
import com.bardab.budgettracker.model.categories.VariableExpenses;
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

    List<String> variablesList = Categories.getCategoriesNames(new VariableExpenses());
    List<String> fixedList = Categories.getCategoriesNames(new FixedExpenses());


    public TransactionsFXController() {
        transactionDao = new TransactionDao(HibernateUtil.getInstance().getSessionFactory());


    }

    @FXML
    private List<String> filteredList = new ArrayList<>();

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
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;

//    @FXML
//    private Label transactionTableLabel;

    @FXML
    private ContextMenu tableContextMenu;

    private Integer monthCode = MonthCode.createIntMonthCodeFromLocalDate(LocalDate.now());

    public void init()  {
        dateFrom.setValue(LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),1));
        dateTo.setValue(LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().lengthOfMonth()));

        createMenuCheckBoxes(variablesList, variableCategoriesMenu,5);
        createMenuCheckBoxes(fixedList,fixedCategoriesMenu,5);

        filteredList.forEach(e-> System.out.println(e));
        listFilteredTransactions();
        setToolTip();
//        transactionTableLabel.setText(MonthCode.monthYear(monthCode));


    }

    public void setToolTip(){
    descriptionColumn.setCellFactory(TooltippedTableCell.forTableColumn());
    }






    public void createMenuCheckBoxes(List<String> list, Menu menu, int numberOfRows){
        GridPane gp = new GridPane();
        List<CheckBox> checkBoxes = new ArrayList<>();
        int x = 0;
        int y = 0;
        for(String category:list) {
            CheckBox cb = new CheckBox(Categories.getPresentableCategoryName(category));
            cb.setStyle("-fx-text-fill: -fx-text-base-color");
            cb.selectedProperty().setValue(true);
            filteredList.add(category);

            cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    if(t1){
                        filteredList.add(category);
                    }
                    else filteredList.remove(category);
                }
            });
            gp.add(cb,x,y);
            if(y<numberOfRows){
                y++;
            }
            else {
                y=0;
                x++;
            }
            checkBoxes.add(cb);
        }
        Button addAll = new Button("Mark all");
        addAll.setOnAction(e-> markAllCheckBoxes(checkBoxes));
        GridPane.setHalignment(addAll, HPos.LEFT);
        gp.add(addAll,x,y+1);

        Button unmarkAll = new Button("Unmark All");
        unmarkAll.setOnAction(e-> unmarkAllCheckBoxes(checkBoxes));
        GridPane.setHalignment(unmarkAll, HPos.RIGHT);
        gp.add(unmarkAll,x,y+1);


        CustomMenuItem cmi = new CustomMenuItem(gp);
        cmi.setHideOnClick(false);

        menu.getItems().add(cmi);
    }


    public void markAllCheckBoxes(List<CheckBox> checkBoxes){
        checkBoxes.forEach(e->e.selectedProperty().setValue(true));
    }
    public void unmarkAllCheckBoxes(List<CheckBox> checkBoxes){
        checkBoxes.forEach(e->e.selectedProperty().setValue(false));
    }

//
//    public void nextMonthTransactions(){
//        this.monthCode = MonthCode.getNextMonthCode(monthCode);
//        listAllTransactions(this.monthCode);
//        transactionTableLabel.setText(MonthCode.monthYear(monthCode));
//
//    }
//    public void previousMonthTransactions(){
//        this.monthCode = MonthCode.getPreviousMonthCode(monthCode);
//        listAllTransactions(this.monthCode);
//        transactionTableLabel.setText(MonthCode.monthYear(monthCode));
//    }




    public void listFilteredTransactions(){
        Task<ObservableList<Transaction>> task = new GetTransactionsFiltered(dateFrom.getValue(),dateTo.getValue(),filteredList);
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


    class GetTransactionsFiltered extends Task {

        private LocalDate dateFrom;
        private LocalDate dateTo;
        private List<String> categories;


        public GetTransactionsFiltered(LocalDate dateFrom, LocalDate dateTo, List<String> categories) {
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
            this.categories = categories;
        }

        @Override
        public ObservableList<Transaction> call() {
            List<Transaction> transactions = transactionDao.getAllTransactions(dateFrom,dateTo,categories);
            transactions.forEach(e->e.setPresentableCategory(Categories.getPresentableCategoryName(e.getCategory())));

            return FXCollections.observableArrayList(transactions);
        }
    }



}
