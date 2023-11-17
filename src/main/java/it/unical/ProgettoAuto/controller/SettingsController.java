package it.unical.ProgettoAuto.controller;




import com.jfoenix.controls.JFXCheckBox;
import it.unical.ProgettoAuto.Main;
import it.unical.ProgettoAuto.config.Settings;
import it.unical.ProgettoAuto.view.SceneHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.net.URL;



public class SettingsController {
    @FXML
    private FlowPane container;

    public void initialize() {

        //carico il font pixeboy in font
        URL path = Main.class.getResource("Resources/Pixeboy.ttf");
        javafx.scene.text.Font font = javafx.scene.text.Font.loadFont(String.valueOf(path), 23);

        container.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(4), new Insets(1))));

        //aggiunta delle checkbox in un vbox da aggiungere al container
        VBox box= new VBox();
        box.setSpacing(25);

        JFXCheckBox audio = new JFXCheckBox("suono");
        audio.setFont(font);
        audio.setTextFill(Color.BLACK);
        audio.getStyleClass().add("custom-jfx-check-box");
        if (Settings.audio){
            audio.selectedProperty().setValue(true);}
        audio.setCheckedColor(Color.BLACK);
        audio.setUnCheckedColor(Color.BLACK);

        //listener per la variabile booleana audio
        audio.selectedProperty().addListener((ov, old_val, new_val) -> Settings.audio=!Settings.audio);



        JFXCheckBox Vibrazione = new JFXCheckBox("vibrazione");
        Vibrazione.setFont(font);
        Vibrazione.setTextFill(Color.BLACK);
        Vibrazione.getStyleClass().add("custom-jfx-check-box");
        if (Settings.vibrazione){
            Vibrazione.selectedProperty().setValue(true);}
        Vibrazione.setCheckedColor(Color.BLACK);
        Vibrazione.setUnCheckedColor(Color.BLACK);

        //listener per la variabile booleana vibrazione
        Vibrazione.selectedProperty().addListener((ov, old_val, new_val) -> Settings.vibrazione=!Settings.vibrazione);
        box.getChildren().addAll(audio,Vibrazione);
        container.getChildren().addAll(box);
    }

    public void IMPOSTAZIONI() {
        SceneHandler.getInstance().createMenu();
    }
    public void ESCI() {
        SceneHandler.getInstance().close();
    }
    public void CLASSIFICA() {
        SceneHandler.getInstance().createclassifica();
    }
    public void LOORE() {
        SceneHandler.getInstance().createstoria();
    }
    public void START() { SceneHandler.getInstance().LunchGame(); }

}
