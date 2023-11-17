package it.unical.ProgettoAuto.view;

import it.unical.ProgettoAuto.Main;
import it.unical.ProgettoAuto.game.Game;
import javafx.embed.swing.SwingNode;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class SceneHandler {

    private static final SceneHandler instance = new SceneHandler();
    private Stage stage;

    private Scene scene;

    private int highScore;


    public static SceneHandler getInstance() {
        return instance;
    }

    private SceneHandler() {
    }

    public void init(Stage stage) {
        highScore = getHSFromFile();
        if (this.stage == null) {
            this.stage = stage;
            this.stage.setTitle("Pizza Deliver");
            createMenu();
            this.stage.setScene(scene);
            Image icon = new Image("/car1.png");
            stage.getIcons().add(icon);
            stage.setOnCloseRequest(e -> Game.shutdown());
            this.stage.show();
        }
    }

    private int getHSFromFile(){
        String HS = "0";
        try {
            File myObj = new File("scores.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                HS = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ops, sembra il file sores.txt sia assente.");
            System.out.println("Non temere, lo creerò io appena finisci la tua prima partita!");
        }
        return Integer.parseInt(HS);
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    private <T> T loadRootFromFXML(String resourceName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(resourceName));
        return fxmlLoader.load();
    }
    public void createMenu() {
        try {
            if (scene == null)
                scene = new Scene(loadRootFromFXML("Main_menu.fxml"));
            else
                scene.setRoot(loadRootFromFXML("Main_menu.fxml"));
            stage.setWidth(698);
            stage.setHeight(499);
            stage.setResizable(false);
        } catch (IOException ignored) {
        }
    }

    public void LunchGame() {
        final SwingNode swingNode = new SwingNode();
        createSwingContent(swingNode);
        StackPane pane = new StackPane();
        pane.getChildren().add(swingNode);
        scene.setRoot(pane);
    }

    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            try {
                swingNode.setContent(new Game());
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException | InterruptedException |
                     FontFormatException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void reLunchGame() throws InterruptedException, IOException, LineUnavailableException {
        Platform.runLater(() -> {
    final SwingNode swingNode = new SwingNode();
    createSwingContent(swingNode);
    StackPane pane = new StackPane();
    pane.getChildren().add(swingNode);
    scene.setRoot(pane);
        });
}



    public void close() {
        stage.close();
    }

    public void createclassifica() {
        try {
            scene.setRoot(loadRootFromFXML("Score_menu.fxml"));

        } catch (IOException ignored) {
        }
    }

    public void createimpostazioni() {
        try {
            scene.setRoot(loadRootFromFXML("Settings_menu.fxml"));
        } catch (IOException ignored) {
        }
    }


    public void createstoria() {
        try {
            scene.setRoot(loadRootFromFXML("Storia_menu.fxml"));

        } catch (IOException ignored) {
        }
    }
}

