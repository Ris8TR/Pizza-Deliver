package it.unical.ProgettoAuto;

import it.unical.ProgettoAuto.view.SceneHandler;
import javafx.application.Application;
import javafx.stage.Stage;


public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        SceneHandler.getInstance().init(stage);
        SceneHandler.getInstance().createMenu();
    }

    public static void main(String[] args) {
        launch();
    }
}