module it.unical.Aspetto_Corsa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;
    requires com.jfoenix;
    requires org.jetbrains.annotations;
    requires jdk.accessibility;
    requires javafx.swing;


    opens it.unical.ProgettoAuto to javafx.fxml;
    exports it.unical.ProgettoAuto;
    exports it.unical.ProgettoAuto.controller;
    opens it.unical.ProgettoAuto.controller to javafx.fxml;
    exports it.unical.ProgettoAuto.view;
    opens it.unical.ProgettoAuto.view to javafx.fxml;
    exports it.unical.ProgettoAuto.game;
    opens it.unical.ProgettoAuto.game to javafx.fxml;
    exports it.unical.ProgettoAuto.config;
    opens it.unical.ProgettoAuto.config to javafx.fxml;

}