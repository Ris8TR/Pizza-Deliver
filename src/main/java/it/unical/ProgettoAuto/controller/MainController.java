package it.unical.ProgettoAuto.controller;


import it.unical.ProgettoAuto.view.SceneHandler;




public class MainController {

    public void IMPOSTAZIONI() {
        SceneHandler.getInstance().createimpostazioni();
    }
    public void ESCI() {
        SceneHandler.getInstance().close();
    }
    public void CLASSIFICA() { SceneHandler.getInstance().createclassifica(); }
    public void LOORE() {
        SceneHandler.getInstance().createstoria();
    }
    public void START() {
        SceneHandler.getInstance().LunchGame();
    }
}


