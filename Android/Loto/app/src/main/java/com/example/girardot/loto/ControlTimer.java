package com.example.girardot.loto;


import android.os.Handler;
import android.util.Log;
import android.view.View;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ControlTimer implements Runnable {

    Model m;
    VueLoto v;
    Handler actualHandler; //Permet de savoir quel Handler à enclencher ce Runnable
    long nbMillisecondes; //Permet de savoir tous les combiens de temps il faut exécuter ce "Timer" de manière répété

    private static String TAG = "ControlTimer";


    public ControlTimer(Model m, VueLoto v) {
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

    private void prerun() {
        actualHandler.postDelayed(this, nbMillisecondes);
    }

    public void run() {
        if(actualHandler == v.handlerDebut) {
            reduitTimer();
            if(m.getNbSecondesVerif() <= 0) {
                v.regardezGrille.setVisibility(GONE);
                v.reclame.setVisibility(VISIBLE);
                v.nombre.setVisibility(VISIBLE);
                m.setInAction(false);
                start(v.handlerReact, 5000);
            } else {
                prerun();
            }
        } else if(actualHandler == v.handlerReact) {
            if(m.isInAction()) {
                return;
            }

            Log.d(TAG, "Aucune réaction");

            Case caseCorrespond = m.nombreActuDansGrille();
            if(caseCorrespond == null) {
                Log.d(TAG, "Bonne réponse !");
                m.addNombreActuToStatistiques("AB");
                m.nombreActuNonDispo();
            } else {
                Log.d(TAG, "Mauvaise réponse !");
                m.addNombreActuToStatistiques("AM");
                v.erreurEnEvidence(caseCorrespond);
                v.perdVieAffichage();
            }
            if(!m.isInAction()) { //Si la partie s'est finit par la perte d'une vie, il ne faut pas que le jeu redémarre le timerReact
                v.restartTimerReact();
                v.changeNombre();
            }
        } else if (actualHandler == v.evidenceErreur) {
            for(int x = 0; x < Grille.getNbLignes(); x++) {
                for (int y = 0; y < Grille.getNbColonnes(); y++) {
                    if(m.getGrille().getCases()[x][y].isEstErreur()) {
                        v.grille[x][y].setBackgroundResource(R.drawable.button_background_blanc);
                        m.getGrille().getCases()[x][y].setEstErreur(false);
                    }
                }
            }
        }
    }

    private void reduitTimer() {
        m.reduitNbSecondesVerif();
        v.changeRegardezGrille();
    }

}