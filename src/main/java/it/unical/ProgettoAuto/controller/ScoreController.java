package it.unical.ProgettoAuto.controller;


import it.unical.ProgettoAuto.Main;
import it.unical.ProgettoAuto.view.SceneHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.scene.text.Font;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ScoreController {

    @FXML
    private FlowPane container ;

    public void initialize() {
        List<Label> labels = new ArrayList<>();
        List<String> scores = getScores();

        //carico il font pixeboy in font
        URL path = Main.class.getResource("Resources/Pixeboy.ttf");
        Font font = Font.loadFont(String.valueOf(path), 26);

        int scMin, scMax;
        container.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(4), new Insets(1))));

        if(scores.size() > 7){
            scMin = scores.size()-7;
        } else {
            scMin = 0;
        }
        scMax = scores.size()-1;

        if (scores.size()==0){
            //se non ci sono punteggi o non esiste il file scores.txt
            Label space = new Label();
            space.setText("");
            Label titolo = new Label();
            titolo.setFont(font);
            titolo.setTextAlignment(TextAlignment.CENTER);
            titolo.setWrapText(true);
            titolo.setPrefSize(110,200);
            titolo.setText("  per ora  nessun punteggio " +
                    ":(" );
            titolo.setTextFill(Color.BLACK);
            container.getChildren().addAll(titolo,space);
        }else {
            //altrimenti scrive i punteggi nel container
            for (int i = scMax; i >= scMin; i--) {
                Label label = new Label();
                label.setTextAlignment(TextAlignment.LEFT);
                label.setFont(font);
                label.setText((scores.size() - i) + ")      " + scores.get(i));
                label.setTextFill(Color.BLACK);
                labels.add(label);
                container.getChildren().addAll(label);
            }
        }

    }

    public List<String> getScores(){
        List<String> scoresFromFile = new ArrayList<>();
        try {
            File myObj = new File("scores.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                scoresFromFile.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Il file scores.txt è assente. Gioca prima di entrare qui! ");
        }
        return scoresFromFile;
    }
    public void IMPOSTAZIONI() { SceneHandler.getInstance().createimpostazioni(); }
    public void ESCI() { SceneHandler.getInstance().close(); }
    public void CLASSIFICA() { SceneHandler.getInstance().createMenu(); }
    public void LOORE() { SceneHandler.getInstance().createstoria(); }
    public void START() { SceneHandler.getInstance().LunchGame(); }
}

