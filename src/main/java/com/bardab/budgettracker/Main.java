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
    }
}




