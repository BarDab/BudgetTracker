package com.bardab.budgettracker;
import com.bardab.budgettracker.gui.controllers.MainWindowFXController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application  {
    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/fxmls/mainWindow.fxml"));
        Parent root = fxmlLoader.load();
        MainWindowFXController controller = fxmlLoader.getController();
        controller.init();

        primaryStage.setTitle("Budget Tracker");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}




