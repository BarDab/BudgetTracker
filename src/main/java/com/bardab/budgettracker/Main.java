package com.bardab.budgettracker;

import com.bardab.budgettracker.gui.controllers.MainWindowFXController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main

extends Application  {

@Override
public void start(Stage primaryStage) throws Exception{



        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/fxmls/mainWindow.fxml"));
        Parent root = fxmlLoader.load();
        System.out.println(fxmlLoader.getLocation());
        MainWindowFXController controller = fxmlLoader.getController();
        controller.init(primaryStage);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 1062, 660);

        primaryStage.setScene(scene);
        primaryStage.show();



        }






//        public void populateDBWithRandomTransactions(){
//                VariableExpenses variableExpenses = new VariableExpenses();
//                TransactionDao transactionDao = new TransactionDao(HibernateUtil.getInstance().getSessionFactory());
//
//                LocalDate localDate = LocalDate.of(2020,01,01).plusDays(1);
//                List<String> categories = VariableExpenses.getCategoriesNames(variableExpenses);
//                for(String category:categories){
//                        localDate = localDate.plusDays(1);
//                        for(int i = 0;i<=6;i++){
//                                Transaction transaction = new Transaction();
//                                transaction.setCategory(categories.get((int) (Math.random()*categories.size())));
//                                transaction.setTransactionDateAndMonthCode(localDate);
//                                transaction.setValue(Math.random()*300);
//                                transactionDao.addEntity(transaction);
//                        }
//
//                }
//
//
//        }
        }