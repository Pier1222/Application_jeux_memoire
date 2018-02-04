package com.example.adrie.piano2.controller;


import android.os.Handler;
import android.util.Log;

import com.example.adrie.piano2.R;

public class ControlTimer implements Runnable {

    Model m;
    Vue v;
    Handler actualHandler; //Permet de savoir quel Handler à enclencher ce Runnable
    long nbMillisecondes; //Permet de savoir tous les combiens de temps il faut exécuter ce "Timer" de manière répété


    public ControlTimer(Model m, Vue v) {
        this.m = m;
        this.v = v;
        actualHandler = null;
        nbMillisecondes = 0;
    }

    public void start(Handler handler, long nbMillisecondes) {
        actualHandler = handler;
        this.nbMillisecondes = nbMillisecondes;
        prerun();
    }

    protected void prerun() {
        actualHandler.postDelayed(this, nbMillisecondes);
    }

    public void run() {
        if(actualHandler == v.handlerClignotement) {
            for(int i = 0; i < v.piano.length; i++) {
                if(m.getTouches()[i].isEstActif()) {
                    if(m.getTouches()[i].getCouleur() == Couleur.BLANC) {
                        v.piano[i].setBackgroundResource(R.drawable.button_background_blanc);
                    } else if(m.getTouches()[i].getCouleur() == Couleur.NOIR) {
                        v.piano[i].setBackgroundResource(R.drawable.button_background_noir);
                    }
                    m.getTouches()[i].setEstActif(false);
                }
            }
                /*for (int i = 0; i < v.touches.length; i++) {
                    for (int j = 0; j < v.touches.length; j++) {
                        if (m.getTouches()[i][j].isEstActif()) {
                            if(m.getTouches()[i][j].getCouleur() == Couleur.BLANC) {
                                v.touches[i][j].setBackground(Color.WHITE);
                            } else if (m.getTouches()[i][j].getCouleur() == Couleur.NOIR) {
                                v.touches[i][j].setBackground(Color.BLACK);
                            }
                            m.getTouches()[i][j].setEstActif(false);
                            if(!v.timerJoueTouche.isRunning()) //Pour ne pas laisser accidentellement la main à l'utilisateur pendant qu'une séquence est jouer
                                m.setInAction(false);
                            v.timerClignotementTouche.stop();
                        }
                    }*/
        }
    }
}
