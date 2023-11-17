package it.unical.ProgettoAuto.controller;

import it.unical.ProgettoAuto.Main;
import it.unical.ProgettoAuto.view.SceneHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.net.URL;


public class StoryController {
    @FXML
    private FlowPane container;

    public void initialize() {
        //carico il font pixeboy in font
        URL path = Main.class.getResource("Resources/Pixeboy.ttf");
        Font font = Font.loadFont(String.valueOf(path), 19);

        container.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(4), new Insets(0))));

        Label label = new Label();
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(font);
        label.setWrapText(true);
        label.setPrefSize(380,280);
        label.setText("""
                Sei mario, un simpatico raider con poteri sovrannaturali, i quali ti permettono di poterti spostare nello spazio in meno di un millisecondo. Purtroppo i tuoi poteri funzionano poco e male. Diciamo che si attivano solo sulla strada statale 522 direzione Pizzo e ti permettono solo di cambiare corsia istantaneamente. Ma questo non ti fermera'!

                Consegna le pizze sfidando le leggi dello stato e della fisica usando: le frecce o i tasti W ed S.\s
                Puoi persino mettere in pausa il gioco con ESC!!!!
                """);
        label.setTextFill(Color.BLACK);
        container.getChildren().add(label);
    }

    public void ESCI() {
        SceneHandler.getInstance().close();
    }
    public void INDIETRO() { SceneHandler.getInstance().createMenu(); }
}
