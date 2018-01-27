package com.example.girardot.boulier;


import android.os.Handler;

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

    private void prerun() {
        actualHandler.postDelayed(this, nbMillisecondes);
    }

    public void run() { //Pour rendre l'application plus protéger, il aurait été souhaitable que run soit private mais c'est impossible car elle est implémenter
        if(actualHandler == v.handlerDebut) {
            reduitTimer();
            if (m.getNbSecondesVerif() > 0) {
                System.out.println(m.getNbSecondesVerif());
                prerun(); //A la différence du Timer de Java, il faut dire quand est-ce qu'on veut continuer la boucle et pas quand on veut l'arrêter
            } else {
                v.deColorHaut();
                m.setInAction(false);
            }
        }
    }

    /*public void actionPerformed(ActionEvent e) {

        if(e.getSource() == v.timerDebut) {
            reduitTimer();
            if (m.getNbSecondesVerif() <= 0) {
                v.visibiliteTentative();

                m.setInAction(false);
                v.deColorHaut();
                v.timerDebut.stop();
            }
        } else if(e.getSource() == v.timerFinEssaie) {
            v.visibiliteTentative();

            //Remet les cases du bas fausses à vide
            m.resetInactivesBas();
            v.colorBas();

            v.resetResultats();
            m.setInAction(false);
            v.deColorHaut();
            v.timerFinEssaie.stop();
        }
    }*/

    private void reduitTimer() {
        m.reduitNbSecondesVerif();
        //v.changeRegardezSequence();
    }

}
