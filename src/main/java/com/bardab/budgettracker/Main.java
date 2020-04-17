package com.bardab.budgettracker;
import com.bardab.budgettracker.dao.FixedCostsDao;
import com.bardab.budgettracker.gui.controllers.MainWindowFXController;
import com.bardab.budgettracker.util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Hibernate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main extends Application  {
    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/fxmlfiles/mainWindow.fxml"));
        Parent root = fxmlLoader.load();
        MainWindowFXController controller = fxmlLoader.getController();
        controller.init();

        primaryStage.setTitle("Budget Tracker");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
//        FixedCostsDao fixedCostsDao = new FixedCostsDao(HibernateUtil.getInstance().getSessionFactory());
//
//        LinkedHashMap<String,Double> hashMap = fixedCostsDao.getFixedCostsTypes();
//        for(String name:hashMap.keySet()){
//            System.out.println(name +" "+ hashMap.get(name));
//        }


    }}

//{
//    public static void main(String[] args) {
//        BudgetForecast budgetForecast = new BudgetForecast();
//        budgetForecast.setIncome(2000.0);
//        budgetForecast.setFixedSpending(1000.0);
//        budgetForecast.setSavingGoal(250.0);
//        budgetForecast.setMonthCode("2020_5");
//
//        BudgetForecastDao budgetForecastDao = new BudgetForecastDao(HibernateUtil.getInstance().getSessionFactory());
//        budgetForecastDao.addTransaction(budgetForecast);
//    }}



