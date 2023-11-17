package it.unical.ProgettoAuto.controller;

import it.unical.ProgettoAuto.game.Game;
import it.unical.ProgettoAuto.view.SceneHandler;
import org.jetbrains.annotations.NotNull;


import javax.sound.sampled.LineUnavailableException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;



public class GameController extends KeyAdapter {

    private final Game game;
    public GameController(Game game) {
        this.game =  game;
    }


    public void keyTyped (KeyEvent e){}


    //eventi alla pressione del tasto X
    public void keyPressed (@NotNull KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
                case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                    //prima controllo se il gioco non è in pausa o è finito
                    if (!game.getstatus() && game.getpause()) {
                        game.moveup();
                    }
                }
                case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                    //prima controllo se il gioco non è in pausa o è finito
                    if (!game.getstatus() && game.getpause()) {
                        game.movedown();
                }
            }
            case KeyEvent.VK_R -> {
                //se il gioco è finito
                if (game.getstatus()){
                    game.stop();
                    try {
                        //riavvio
                        SceneHandler.getInstance().reLunchGame();
                    } catch (InterruptedException | IOException | LineUnavailableException ex) {
                        ex.printStackTrace();}
                }
            }
            case KeyEvent.VK_F -> {
                //se il gioco è finito
                if (game.getstatus()){
                    game.stop();
                    try {
                        //torno al menu
                        SceneHandler.getInstance().createMenu();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            case KeyEvent.VK_ESCAPE -> {
                    //pausa
                if (game.getpause() && !game.getstatus()) {
                    game.setpause();
                    game.stop();
                }
                else{
                    //avvio
                    if (!game.getstatus()) {
                        game.start();

                    }
                }
            }
            default -> {
            }
        }
    }
}

